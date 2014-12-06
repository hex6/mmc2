(ns mmc2.pathfinding
  (:require [clojure.set]))

(def directions
  {"down" [ 1  0]
   "up"  [-1  0]
   "right"  [ 0  1]
   "left"    [ 0 -1]})


(defn translate-coords
  "Takes x and y coordinates and translates that with delta x and y."
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(defn neighbour-coords
  [coord]
  (map translate-coords (repeat coord) (vals directions)))

(defn walkable
  [layout coord]
  (not= "wall" (get-in layout coord "wall")))

(defn neighbours
  "Returns map of neighbour coords which is not a wall and their parent coords."
  [layout coord]
  (if (walkable layout coord)
    (let [walkable-coords (filter #(walkable layout %) (neighbour-coords coord))]
      (map #(hash-map % coord) walkable-coords))
    ()))


(defn flood-fill
  [layout coord]
  (loop [visited {}
         frontier {coord nil}]
    (if (seq frontier)
      (let [current (first frontier)
            frontier (dissoc frontier (key current))
            visited (merge visited current)
            candidates (first (neighbours layout coord))
            frontier (merge frontier (apply dissoc candidates (keys visited)))]
        (recur visited frontier))
      visited)))

