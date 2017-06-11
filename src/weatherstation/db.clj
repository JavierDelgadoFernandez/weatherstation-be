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

(ns weatherstation.db
  (:use korma.db))

(defdb db (postgres {:host (or (System/getenv "DB_HOST") "localhost")
                     :port (or (System/getenv "DB_PORT") 5432)
                     :db (or (System/getenv "DB_NAME") "weatherstation")
                     :user (or (System/getenv "DB_USER") "postgres")
                     :password (or (System/getenv "DB_PASSWORD") "postgres")
                     }))