(ns mmc2.pathfinding
  (:require [clojure.set]
            [flatland.ordered.map :as ordered]))

(def directions
  {[ 1  0] "down" 
   [-1  0] "up"   
   [ 0  1] "right"
   [ 0 -1] "left"})

(def points
  {"song" 1
   "album" 2
   "playlist" 4
   "banana" 4
   "trap" 1
   "user" 4
   "monkey" 0})


(defn translate-coords
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(defn neighbour-coords
  [coord]
  (map translate-coords (repeat coord) (keys directions)))

(defn walkable
  [layout coord]
  (let [item (get-in layout coord "wall")]
    (not= "wall" item)))
    ;(or (= "empty" item) (= "monkey" item))))

(defn neighbours
  [layout coord]
  (if (walkable layout coord)
    (let [walkable-coords (filter #(walkable layout %) (neighbour-coords coord))]
      (zipmap walkable-coords (repeat coord)))
    ()))

(defn backtrace-path
  [visited coord]
  (loop [path [coord]
         c coord]
    (let [parent (visited c)]
      (if (nil? parent)
        path
        (recur (conj path parent) parent)))))

(defn path-to-dirs
  [path]
  (loop [[f s & xs] path
         dirs []]
    (if (seq s)
      (let [dirs (conj dirs (directions [(- (f 0) (s 0)) (- (f 1) (s 1))]))]
        (recur (cons s xs) dirs))
      (rseq dirs))))


(defn flood-fill
  [layout coord]
  (loop [visited {}
         frontier (ordered/ordered-map coord nil)
         paths []]
    (if (seq frontier)
      (let [current (first frontier)
            item (get-in layout (key current))
            frontier (dissoc frontier (key current))
            visited (merge visited current)
            candidates (neighbours layout (key current))
            frontier (if (or (= "empty" item) (= "monkey" item))
                       (let [front (apply dissoc candidates (keys visited))]
                         (into frontier front))
                       frontier)
            paths (if (and (not= "empty" item) (not= "monkey" item))
                    (let [path (path-to-dirs (backtrace-path visited (key current)))
                          user (= "user" item)]
                      (conj paths {:origcoord coord
                                   :destcoord (key current)
                                   :path path
                                   :user user
                                   :points (/ (if (nil? (points item)) 0 (points item)) (count path))}))
                    paths)]
        (recur visited frontier paths))
      paths)))

