(ns mmc2.ai
  (:require [mmc2.pathfinding :as path]))

(defn move
  [state path prio]
  (def inventory (get state "inventory"))
  (def last-item (peek inventory))
  (cond
    (= "banana" last-item)
      {:prio prio
       :path path
       :state (into state {:command "use"
                           :item "banana"})}

    (= "trap" last-item)
      {:prio prio
       :path path
       :state (into state {:command "use"
                           :item "trap"})}

    (and (>= (count inventory) (get state "inventorySize")) (not= true prio))
      (let [paths (path/flood-fill (get state "layout") (get state "position"))
            path (:path (first (sort-by (comp count :path)
                                        (filter #(= -1 (:points %)) paths))))]
        (println "Going to user!")
        {:path (rest path)
         :prio true
         :state (into state {:command "move"
                             :direction (first path)})})

    (not (seq path))
      (let [paths (path/flood-fill (get state "layout") (get state "position"))
            path (:path (first (sort-by (juxt (comp count :path) (comp - :points))
                                        (filter #(not= -1 (:points %)) paths))))]
        (println "New path!")
        {:path (rest path)
         :state (into state {:command "move"
                             :direction (first path)})})
      
    (= "speedy" (first (keys (get state "buffs"))))
      {:prio prio
       :path (drop 2 path)
       :state (into state {:command "move"
                           :directions (take 2 path)})}

    :else
      (do
        (println "just walking")
        {:prio prio
         :path (rest path)
         :state (into state {:command "move"
                             :direction (first path)})})))
       

