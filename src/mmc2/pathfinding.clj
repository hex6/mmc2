(ns mmc2.pathfinding)

(def directions
  {"right" [ 1  0]
   "left"  [-1  0]
   "down"  [ 0  1]
   "up"    [ 0 -1]})


(defn translate-coords
  "Takes x and y coordinates and translates that with delta x and y."
  [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])


(defn neighbours
  [layout coord]
  (let [neighbour-coords (map translate-coords (repeat coord) (vals directions))]
    (filter #(not= "wall" (get-in layout (reverse %) "wall")) neighbour-coords)))


(defn flood-fill
  [layout coord]
  (loop [visited #{}
         open #{coord}]
    (if (seq open)
      (let [current (first open)
            visited (conj visited current)
            open (disj open current)
            candidates (set (neighbours layout coord))
            open (clojure.set/union open (clojure.set/difference candidates visited))]
        (recur visited open))
      visited)))

