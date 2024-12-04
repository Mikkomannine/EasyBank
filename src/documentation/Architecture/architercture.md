
# **Application Structure**
The EasyBank project is built in a modular way by implementing MVC model structure to ensure efficient maintenance and scalability. Below is a brief description of the folder structure and the roles of the files:

____

### **1. config**
- **Purpose:** Contains configuration classes for the application.
- Classes:
    - `SecurityConfig.java`: Configures security settings for the application.
    - `LocaleConfig.java`: Configures the locale settings for the application.

____

### **2. controllers**
- **Purpose:** Defines the application's REST API endpoints.
- Each controller handles requests of specific functionality of the application and is connected to certain service class.
- Classes:
    - `BankAccountController.java`: Manages user's bankaccount requests.
    - `TransactionController.java`: Handles requests related to money transfers.
    - `NotificationController.java`: Manages notification requests.
    - `UserController.java`: Handles user-related requests.

____

### **3. db**
- **Purpose:** Database interactions.
- Contains files or classes responsible for interacting with the database.
- Classes:
    - `BankAccountRepository.java`: Interface for database operations related to bank accounts.
    - `TransactionRepository.java`: Interface for database operations related to transactions.
    - `UserRepository.java`: Interface for database operations related to users.
    - `NotificationRepository.java`: Interface for database operations related to notifications.

____

### **4. exceptions**
- **Purpose:** Centralized error handling for the application.
- This folder contains all custom exceptions used within the application.
- Classes:
    - `UnauthorizedException.java`: Thrown when a user is not authorized to access a resource.
    - `UsernameExistsException.java`: Thrown when a user tries to register with an existing username.

____

### **5. models**
- **Purpose:** Data models that represent database tables and application entities.
- Classes:
    - `User.java`: Contains user-related information, such as name, email, and hashed password.
    - `Transaction.java`: Represents transaction details like sender, recipient, and amount.
    - `BankAccount.java`: Represents bank account details like balance, account number, and owner.
    - `Notification.java`: Represents notification details like content and timestamp.

____

### **6. services**
- **Purpose:** Business logic layer of the application.
- Each service acts as an intermediary between the controllers and the database layer, performing calculations, validations, and other business rules.
- Classes:
    - `BankAccountService.java`: Handles bankaccount operations like balance checks.
    - `TransactionService.java`: Processes money transfers and ensures transaction validity.
    - `NotificationService.java`: Manages notification creation and retrieval.
    - `UserService.java`: Handles user-related operations like registration and login.

____

### **7. ui**
- **Purpose:** Contains files or logic related to the user interface.
- This folder includes Vaadin components, templates, or API integrations with the front-end.
- Classes:
    - `LoginView.java`: Manages the functionality of the login page.
    - `RegisterView.java`: Manages the registration page.
    - `MainView.java`: Manages the main page of the application.
    - `ProfileView.java`: Manages the user profile page.

____

### **8. utils**
- **Purpose:** Utility functions and helper tools.
- This folder contains reusable classes, such as data conversions, date handling, or helper functions.
- Classes:
    - `JwtUtil.java`: Generates and validates JSON Web Tokens for user authentication.

____

### **Root File**
- **EasyBankProjectApplication.java:** The main entry point of the application. This file initializes the Spring Boot application.


