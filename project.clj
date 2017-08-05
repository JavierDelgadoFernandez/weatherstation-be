(defproject weatherstation "0.1.0"
  :description "Weather station backend."
  :dependencies [[compojure "1.6.0"]
                 [clojure-future-spec "1.9.0-alpha17"]
                 [graphql-clj "0.2.5" :exclusions [org.clojure/clojure]] 
                 [korma "0.4.3"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/core.match "0.3.0-alpha5"]
                 [org.postgresql/postgresql "42.1.4"]
                 [ring-cors "0.1.11"]
                 [ring/ring-core "1.6.2"]
                 [ring/ring-defaults "0.3.1"]
                 [ring/ring-jetty-adapter "1.6.2"]
                 [ring/ring-json "0.4.0"]]
  :uberjar-name "weatherstation-standalone.jar"
  :plugins [[lein-ring "0.12.0"]]
  :ring {:handler weatherstation.handler/app
         :auto-reload? true
         :port 3002})
