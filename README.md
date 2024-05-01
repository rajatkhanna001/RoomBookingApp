# RoomBooking
This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 16.2.6.

Frontend: (Angular)

Prerequisites:

Git
MongoDB
Node - v16.14.0
Npm - 8.3.1
IDE - VS Code, Eclipse, etc.
Java - 17

Steps to setup & run the frontend project:

1. Clone the repository using git clone.
2. Open cmd and navigate to the "room-booking" folder and run below command.
            ng serve
3. This will start your server on localhost:4200.
4. open the URL on browser.

# Node packages
Run `npm install` for installing all the required packages in "room-booking" folder using cmd.

## Development server
Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Build
Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests
Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

Backend: (Java)

Steps to setup & run the frontend project:

1. Make sure that the repository is already cloned while cloning frontend.
2. Import booking folder in any IDE like eclipse.
3. Use Java 17 and run the project.
4. It will run the server on http://localhost:8080/.
5. You can create new Room using endpoint.
            http://localhost:8080/room/create/
6. DB query for rooms collection/table can be found at "/booking/src/main/resources/DB/rooms.json".