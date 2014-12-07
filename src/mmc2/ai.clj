(ns mmc2.ai
  (:require [mmc2.pathfinding :as path]))

(defn move
  [state]
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
      (let [paths (path/flood-fill (get state "layout") (get state "position"))
            path (sort-by :points (comp - compare) (filter #(= (:user %) true) paths))]
        (println "Going to user!" (first path))
        (if (= "speedy" (first (keys (get state "buffs"))))
          (into state {:command "move"
                       :directions (take 2 (:path (first path)))})
          (into state {:command "move"
                       :direction (first (:path (first path)))})))

    :else
      (let [paths (path/flood-fill (get state "layout") (get state "position"))
            path (sort-by :points (comp - compare) (filter #(not= (:user %) true) paths))]
        (println "Walking!" (take 4 path))
        (if (= "speedy" (first (keys (get state "buffs"))))
          (into state {:command "move"
                       :directions (take 2 (:path (first path)))})
          (into state {:command "move"
                       :direction (first (:path (first path)))})))))

