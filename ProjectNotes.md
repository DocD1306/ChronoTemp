### To-Do tasks

- Validate the JSON message content
- Learn how to use coordinates API
- Learn how to use weather API
- Create necessary endpoints
- Create an answer for the frontend when a message enters
- Create a HashMap to determine the country code (Example: "es" for spain)

### Data flow

- User input (city name)
- API endpoint (Rest Controller) 
- city name goes to service class 
- service class calls location API with the String
- recieves an answer with the coordinates and passes it to the weather API
- the weather API answers and the answer is transformed into objects 
- the objects are returned as a JSON to the frontend


