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

(ns weatherstation.services.measure
  (:require [korma.core :as o]
            [weatherstation.entities :as e]
            ))

(defn find-last []
  (o/select e/measure
    (o/order :epoch :DESC)
    (o/limit 1)))

(defn find-to [to]
  (o/select e/measure
    (o/where {:epoch [< to]})))

(defn find-from [from]
  (o/select e/measure
    (o/where {:epoch [> from]})))

(defn find-from-to [from to]
  (o/select e/measure
    (o/where (and {:epoch [> from]}
                  {:epoch [< to]}))))

(defn create [measure]
  (o/insert e/measure
    (o/values
      (update (dissoc measure :date)
        :epoch (fn [e] (or e (System/currentTimeMillis)))))))
