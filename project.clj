(defproject mmc2 "0.1.0-SNAPSHOT"
  :description ""
  :url ""
  :license {:name ""
            :url ""}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [http-kit "2.1.16"]]
  :main ^:skip-aot mmc2.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
