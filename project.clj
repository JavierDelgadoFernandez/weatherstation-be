(defproject weatherstation "0.1.0"
  :description "Weather station backend."
  :dependencies [[compojure "1.5.2"]
                 [clojure-future-spec "1.9.0-alpha13"]
                 [graphql-clj "0.2.2" :exclusions [org.clojure/clojure]] 
                 [korma "0.4.3"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [ring-cors "0.1.8"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-defaults "0.2.1"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [ring/ring-json "0.4.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler weatherstation.handler/app
         :auto-reload? true
         :port 3002})
