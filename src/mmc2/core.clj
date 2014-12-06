(ns mmc2.core
  (:require [mmc2.ai :as ai]
            [org.httpkit.client :as http]
            [clojure.data.json :as json])

  (:gen-class))

(def url "http://competition.monkeymusicchallenge.com/game")
(def api-key "es+XLozfQucTw1amMoCm0xnSIpo=")
(def team-name "Music Benders")

(defn post-to-server
  [url data]
  (let [{:keys [status headers body error] :as res}
        @(http/post
           url
           {:body (json/write-str data)
            :headers {"Content-type" "application/json"}})]

        (if error
          (println "Error: " error)
          (json/read-str body))))


(defn pretty-map
  [layout])
  

(defn -main
  [game-id & args]
  (def data 
    {:apiKey api-key
     :gameId game-id
     :team team-name})

  (let [init-state (post-to-server url (into data {:command "join game"}))]
    (loop [state init-state]
      (if (get state "isGameOver" true)
        (println state)
        (do
          (println state)
          (recur (post-to-server url (into data (ai/move state)))))))))
