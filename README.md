# WeatherStation backend
WeatherStation backend with GraphQL goodies.

## Running the server
Once the repository is cloned (``git clone https://github.com/javierdelgadofernandez/weatherstation``), you can either run directly on your machine (requires [lein](https://leiningen.org) installed) or run it with Docker. In both cases, it will require a PostgreSQL dababase (check ``db.clj`` to see details) running in the host. The server will start on port ``3002``.

### With lein
Using ``liningen`` we will need only one command that will download the libraries and run the server:
```sh
lein ring server-headless   
```

### With Docker
Simply build the container:
```sh
docker build -t weatherstation-be .
```
And run it:
```sh
docker run --network=host weatherstation-be
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
