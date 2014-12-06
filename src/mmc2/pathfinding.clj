(ns mmc2.pathfinding
  (:require [clojure.set]))

(def directions
  {"down" [ 1  0]
   "up"  [-1  0]
   "right"  [ 0  1]
   "left"    [ 0 -1]})


(defn translate-coords
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(defn neighbour-coords
  [coord]
  (map translate-coords (repeat coord) (vals directions)))

(defn walkable
  [layout coord]
  (not= "wall" (get-in layout coord "wall")))

(defn neighbours
  [layout coord]
  (if (walkable layout coord)
    (let [walkable-coords (filter #(walkable layout %) (neighbour-coords coord))]
      (zipmap walkable-coords (repeat coord)))
    ()))


(defn flood-fill
  [layout coord]
  (loop [visited {}
         frontier {coord nil}]
    (if (seq frontier)
      (let [current (first frontier)
            frontier (dissoc frontier (key current))
            visited (merge visited current)
            candidates (neighbours layout (key current))
            frontier (merge frontier (apply dissoc candidates (keys visited)))
            item (get-in layout (key current))]
        ;(cond
          ;(= "song" item)
          ;(= "album" item)
          ;(= "playlist" item)
          ;(= "banana" item)
        (recur visited frontier))
      visited)))

