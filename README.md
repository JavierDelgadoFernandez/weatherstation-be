# WeatherStation backend
WeatherStation backend with GraphQL goodies.

## Running the server
Once the repository is cloned (``git clone https://github.com/javierdelgadofernandez/weatherstation``), run the next command that will start the web server on port ``3002`` using a Postgres database (check ``db.clj`` to see details):
```sh
lein ring server-headless   
```

## Creating some data
And from another terminal, we should be able to send data:
```sh
curl -XPOST localhost:3002/api/measure \
  -H "Content-Type: application/json" \
  -d '{"temperature": 23, "pressure": 22, "humidity": 20, "epoch": 3494701246000}' 
```

## Querying the data using ``GraphQL``
Using the ``GraphQL`` syntax but sending the request as ``JSON``:
### Getting the last measure:
If we do not specify either ``from`` or ``to``, the server returns the last measure:
```sh
curl -XPOST 'localhost:3002/graphql' \
  -H "Content-Type: application/json" \
  -d '{"query": "query { measures {temperature,humidity,epoch}}"}' 
```
### Retrieving several measures:
With parameters ``from`` and ``to`` we can specify a date range to retrive:
```sh
curl -XPOST 'localhost:3002/graphql' \
  -H "Content-Type: application/json" \
  -d '{"query": "query { measures(from: 1494701245000, to:1494701247000) {temperature,humidity,epoch}}"}'
```
Or:
```sh
curl -XPOST 'localhost:3002/graphql' \
  -H "Content-Type: application/json" \
  -d '{"query": "query { measures(from: 1494701245000) {temperature,humidity,epoch}}"}'
```

Or:
```sh
curl -XPOST 'localhost:3002/graphql' \
  -H "Content-Type: application/json" \
  -d '{"query": "query { measures(to: 1494701245000) {temperature,humidity,epoch}}"}'
```
