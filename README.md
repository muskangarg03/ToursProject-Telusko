# Tours & Travels Management System  

A platform to streamline tour management for administrators and provide customers with an intuitive interface to explore and book travel plans. The system ensures secure and efficient data handling for destinations, lodging, transport, and bookings.  


## Features  

### User Modules  
#### **1. Administrator Module**  
- **Location Management**: Add, update, and delete travel destinations.  
- **Lodging Management**: Manage lodging details, including name, type, address, and rating.  
- **Transport Management**: Define transport options associated with tours.  
- **Tour Management**: Consolidate locations, lodging, and transport into cohesive tour plans.  
- **Booking Overview**: Monitor bookings, ticket sales, and customer details.  

#### **2. Customer Module**  
- **Registration/Login**: Register or log in to access personalized features.  
- **Tour Exploration**: Browse tours with filters (e.g., location, price, lodging, transport).  
- **Tour Booking**: Check availability, confirm bookings, and process payments.  

### Functional Modules  
#### - **User Authentication (Role-Based Access)**  
#### - **Location Module**  
#### - **Lodging Module**  
#### - **Transport Module**  
#### - **Tour Module**  
#### - **Tour Booking Module**  


## Installation
### **Clone the Repository**
   ```bash
   git clone https://github.com/muskangarg03/ToursProject-Telusko.git
   cd tours-and-travels-backend
  ```
  
### **Backend Installation with IntelliJ IDEA**

### 1. Open the Project in IntelliJ
- Launch IntelliJ IDEA.
- Click **File** > **Open** and select the `ToursProject-Telusko` folder.
- Import the Maven dependencies.
- Used Versions:
    **JDK Version** : 21
    **Springboot** : 3
    **Spring** : 6
### 2. Configure the Application
- Navigate to `src/main/resources/application.properties`.
- Update the database, cloudinary, stripe, outh2 configuration.

  #### PostgreSQL Configuration
  ```
  spring.datasource.url= jdbc:postgresql://localhost:5432/TeluskoTours
  spring.datasource.username= your_username
  spring.datasource.password= your_password
  spring.datasource.driver-class-name= org.postgresql.Driver
  spring.jpa.properties.hibernate.dialect= org.hibernate.dialect.PostgreSQLDialect
  ```

  #### OAuth2 Configuration
  Steps to Generate Keys and Credentials:
  1. **Google Cloud Console**:  
  Visit [Google Cloud Console](https://console.cloud.google.com/) and log in.
  2. **Create Project**:  
  Click New Project, name it, and create.
  3. **Enable APIs**:  
  Go to **APIs & Services > Library**, enable the following:  
   - Google+ API  
   - Google Identity API
  4. **Create Credentials**:  
  Navigate to **APIs & Services > Credentials**, select **OAuth 2.0 Client ID**, configure the consent screen, and set the redirect URI:  
   ```bash
   http://localhost:8080/login/oauth2/code/google
   ```
   
   ```
  spring.security.oauth2.client.registration.google.client-name= google
  spring.security.oauth2.client.registration.google.client-id= your_client-id
  spring.security.oauth2.client.registration.google.client-secret= your_client-secret
  spring.security.oauth2.client.registration.google.scope= openid, email, profile
  spring.security.oauth2.client.registration.google.redirect-uri= http://localhost:8080/login/oauth2/code/google
  ```


  #### Cloudinary Configuration
  Steps to Generate Keys and Credentials:
  1. **Create Account**:  
  Sign up or log in at Cloudinary.
  2. **Retrieve Credentials**:  
  From the dashboard, copy Cloud Name, API Key, API Secret.
  
  ```
  cloudinary.cloud-name= your_cloud-name
  cloudinary.api-key= your_api-key
  cloudinary.api-secret= your_api-secret
  ```

  #### Stripe Configuration
  Steps to Generate Keys and Credentials:
  1. **Create Account**:  
  Sign up or log in at Stripe.
  2. **Retrieve Credentials**:  
  Go to Developers > API Keys and copy the following:
    - Secret Key (server-side)
    - Publishable Key (client-side)
      
  ```
  stripe.secret.key= your_secret-key
  stripe.publishable.key= your-publishable-key
  ```

### **Frontend Installation with VS Code**

1. **Open Frontend Folder**:  
   To open the frontend folder, navigate to `src/main/Frontend`.

2. **File Initialization**:  
   Create a file named `.env` inside the root directory of the frontend folder.  
   Write the following inside your `.env` file:  
   `VITE_BASE_URL = YOUR BACKEND URL`

3. **Module Initialization**:  
   Inside the frontend folder, run `npm install` at the root path to install the node modules.

4. **Start Project**:  
   Inside the frontend folder, run `npm run dev` at the root path to start your React-Vite application.  
   Most likely, your Vite project will run on the port `5173`. Click on the `https://localhost:5173` URL to open your project in the browser.



  
  









