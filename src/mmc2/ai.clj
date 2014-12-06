(ns mmc2.ai
  (:require [mmc2.pathfinding :as path]))

(defn find-user
  [state]
  )

(defn move
  [state & [path]]
  (def inventory (get state "inventory"))
  (def last-item (peek inventory))
  (cond
    (= "banana" last-item)
      (into state {:command "use"
                   :item "banana"})
    (= "trap" last-item)
      (into state {:command "use"
                   :item "trap"})
    (>= (count inventory) (get state "inventorySize"))
      (find-user state)
    :else
      (into state {:command "move"
                   :direction (rand-nth '("left" "right" "up" "down"))})))

