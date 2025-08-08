# Healthcare Management System

A comprehensive Spring Boot application for healthcare provider and patient management with secure authentication and appointment scheduling.

## 🏥 Features

### Provider Management
- **Provider Registration**: Secure registration with comprehensive validation
- **Provider Authentication**: JWT-based login system
- **Availability Management**: Set, update, and delete time slots
- **Recurring Schedules**: Support for daily, weekly, and monthly patterns

### Patient Management
- **Patient Registration**: HIPAA-compliant patient registration
- **Patient Authentication**: Secure login with JWT tokens
- **Medical History**: Track patient medical conditions
- **Insurance Information**: Manage insurance details

### Appointment System
- **Slot Generation**: Automatic appointment slot creation
- **Booking Management**: Track appointment status
- **Location Support**: Clinic, hospital, telemedicine, and home visit options
- **Pricing Integration**: Flexible pricing with insurance support

## 🛠️ Technical Stack

- **Backend**: Java Spring Boot 3.5.4
- **Database**: PostgreSQL
- **ORM**: JPA/Hibernate
- **Security**: Spring Security + JWT
- **Build Tool**: Maven
- **Validation**: Jakarta Validation

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/ThinkitiveAi/java-node-ai-suit-3.git
   cd java-node-ai-suit-3/health-first-server
   ```

2. **Configure Database**
   - Create a PostgreSQL database
   - Update `application.properties` with your database credentials

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**
   - Base URL: `http://localhost:8088`
   - API Documentation: Available at `/api/v1/`

## 📋 API Endpoints

### Provider Endpoints
- `POST /api/providers/register` - Provider registration
- `POST /api/auth/login` - Provider login
- `POST /api/v1/provider/availability` - Create availability slots

### Patient Endpoints
- `POST /api/v1/patient/register` - Patient registration
- `POST /api/v1/patient/login` - Patient login

## 🔐 Security Features

- **Password Hashing**: BCrypt with 12 salt rounds
- **JWT Authentication**: Secure token-based authentication
- **CORS Configuration**: Cross-origin resource sharing support
- **Input Validation**: Comprehensive request validation
- **HIPAA Compliance**: Secure handling of medical data

## 📊 Database Schema

### Core Entities
- **Provider**: Healthcare provider information
- **Patient**: Patient demographics and medical data
- **ProviderAvailability**: Available time slots
- **AppointmentSlot**: Generated appointment slots

### Key Features
- UUID-based primary keys
- Comprehensive audit trails
- Soft delete support
- Optimized indexing

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Support

For support and questions, please contact the development team or create an issue in the repository.

---

**Built with ❤️ for better healthcare management**