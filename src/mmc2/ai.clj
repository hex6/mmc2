(ns mmc2.ai)

(defn move
  [state]
  (into state {:command "move"
               :direction (rand-nth '("left" "right" "up" "down"))}))
