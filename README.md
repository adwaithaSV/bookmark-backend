# Bookmarking Application

A Spring Boot application that allows users to manage their bookmarks with secure authentication and user management.

## Features

- User Registration and Authentication
- Create, Read, Update, and Delete Bookmarks
- User-specific Bookmark Management
- Bookmark Limit Protection
- Responsive Web Interface using Thymeleaf Templates

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- H2 Database (can be configured for other databases)
- Maven

## Getting Started

### Prerequisites

- JDK 17 or later
- Maven 3.6.x or later
- Your favorite IDE (Spring Tool Suite, IntelliJ IDEA, etc.)

### Installation

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Navigate to the project directory:
```bash
cd Bookmarking
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/Bookmarking/
│   │       ├── config/           # Security configuration
│   │       ├── controller/       # REST and web controllers
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── exception/        # Custom exceptions
│   │       ├── model/           # Entity classes
│   │       ├── repository/      # Data access layer
│   │       └── service/         # Business logic layer
│   └── resources/
│       ├── templates/           # Thymeleaf templates
│       └── application.properties # Application configuration
```

## API Endpoints

### Authentication
- `POST /signup` - Register a new user
- `POST /login` - User login

### Bookmarks
- `GET /bookmarks` - List all bookmarks for the authenticated user
- `POST /bookmarks` - Create a new bookmark
- `PUT /bookmarks/{id}` - Update an existing bookmark
- `DELETE /bookmarks/{id}` - Delete a bookmark
- `GET /bookmarks/{id}` - Get a specific bookmark

## Security

The application implements Spring Security with the following features:
- Password encryption
- Session management
- CSRF protection
- Secure authentication workflow

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Spring Boot Documentation
- Spring Security Documentation
- Thymeleaf Documentation
