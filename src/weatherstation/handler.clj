;  This program is free software: you can redistribute it and/or modify
;  it under the terms of the GNU General Public License as published by
;  the Free Software Foundation, either version 3 of the License, or
;  (at your option) any later version.
; 
;  This program is distributed in the hope that it will be useful,
;  but WITHOUT ANY WARRANTY; without even the implied warranty of
;  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;  GNU General Public License for more details.
; 
;  You should have received a copy of the GNU General Public License
;  along with this program.  If not, see <http://www.gnu.org/licenses/>.

(ns weatherstation.handler
  (:require [compojure.core :refer [POST defroutes]]
            [compojure.route :as route]
            [ring.util.response :as response]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.defaults :refer :all]
            [weatherstation.graphql :as graphql]
            [weatherstation.services.measure :as measure]
            ))

(defn create-measure [request]
  (let [new-measure (measure/create (get-in request [:body]))]
    {:status 201
     :headers {"Location" (str "/measure/" (:id new-measure))}}))

(defroutes routes
  (POST "/api/measure" request (create-measure request))
  (POST "/graphql" request
       (response/response (graphql/execute (get-in request [:body :query])
                                           (get-in request [:body :variables]))))
  (route/not-found "<h1>Route not found</h1>"))


(def app
  (-> routes
    wrap-json-response
    (wrap-cors :access-control-allow-origin [#"http://localhost:8080" #"http://.*"]
               :access-control-allow-methods [:get :put :post :delete])
    (wrap-json-body {:keywords? true})))
