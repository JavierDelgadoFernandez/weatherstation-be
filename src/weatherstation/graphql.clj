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

(ns weatherstation.graphql
  (:require [clojure.core.match :as match] 
            [graphql-clj.executor :as executor]
            [graphql-clj.schema-validator :as sv]
            [weatherstation.services.measure :as measure]
            ))

(def starter-schema "type Measure {
    humidity: Float
    pressure: Float
    temperature: Float
    epoch: Int
  }

  type Query {
    measures(from: Int, to: Int): [Measure]
  }

  schema {
    query: Query
  }")


(defn get-measures [from to]
  (match/match [from to]
    [nil nil] (measure/find-last)
    [ _  nil] (measure/find-from from)
    [nil  _ ] (measure/find-to to)
    [ _   _ ] (measure/find-from-to from to)))

(def validated-schema (sv/validate-schema starter-schema))

(defn starter-resolver-fn [type-name field-name]
  (match/match [type-name field-name]
   ["Query" "measures"] (fn [context parent args] (get-measures (get args "from") (get args "to")))
   :else nil))

(defn execute
  [query variables]
  (executor/execute nil validated-schema starter-resolver-fn query (clojure.walk/stringify-keys variables)))
