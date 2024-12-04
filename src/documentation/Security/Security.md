# Security

____

## Introduction

____

Security is a critical aspect of any software application, especially in the financial sector. The EasyBank application has been designed with security in mind to ensure the protection of user data and transactions. This document provides an overview of the security measures implemented in the EasyBank application to safeguard user information and prevent unauthorized access.

____

## Security Measures

____

The Easybank application incorporates two main security measures to protect user data and preventing unauthorized access:

1. **Authentication**: The application uses a jwt-based authentication mechanism to verify the identity of users. When a user logs in, the application generates a jwt token that is used to authenticate subsequent requests. This ensures that only authenticated users can access the application and perform transactions.
2. **Password Hashing**: User passwords are hashed using the bcrypt algorithm before being stored in the database. This ensures that even if the database is compromised, user passwords remain secure and cannot be easily decrypted.
3. **Login Notifications**: The application sends notifications to users whenever they log in to their account. This allows users to monitor their account activity and detect any unauthorized access.
____

## Future Enhancements

____

In the future, the EasyBank application plans to implement the following security enhancements:
1. **Role-Based Access Control**: Implement role-based access control to restrict access to certain features based on user roles.
2. **Two-Factor Authentication**: Introduce two-factor authentication to add an extra layer of security to user accounts.
3. **Password Policy Enforcement**: Enforce password policies such as minimum length, complexity, and expiration to enhance password security.

____
