# VPD-Money-Kelvin-Iyalo-Test
This is a simple payment simulation application
it was build using Kotlinn and MVVM and Clean Architecture combine.
Room database is used to store the transaction history on the local storage
The application consist of 6 Screens
1. the Login Screen which takes email and password for authentication 
![image](https://github.com/KelvinIyalo/VPD-Money-Kelvin-Iyalo-Test/assets/53624560/2ff5e6b0-363f-4d8c-8e27-9530d6f9211e)

2. User Registration Screen it take email and password for registration
![image](https://github.com/KelvinIyalo/VPD-Money-Kelvin-Iyalo-Test/assets/53624560/8e0044e6-fd51-4ba2-bc60-e03fba2988ea)

3. Home Screens
   it has a recyclerview that displays a list of accounts owned by the user for a mock data stored in a sharedpreference.
   The Fund Transfer button onclick the app will navigate to the fund transfer screen
   ![image](https://github.com/KelvinIyalo/VPD-Money-Kelvin-Iyalo-Test/assets/53624560/152ef371-4ec0-4149-975b-675db2712375)

4. The Fund Transfer screen, it has drop field to select account to transfer from. the proceed button will be set to Enabled whenever all the available field are properly filled.
      ![image](https://github.com/KelvinIyalo/VPD-Money-Kelvin-Iyalo-Test/assets/53624560/b7cfb8cb-8a23-43df-a222-5618080d2007)

5. The Reciept Screen, It shows a summary of the transaction made
   ![image](https://github.com/KelvinIyalo/VPD-Money-Kelvin-Iyalo-Test/assets/53624560/718975a0-a240-48cc-9a10-21641b9d53c0)

6. The Manage Account Screen, The User accounts can be funded from here and user can also sign out of the currenlt singed in account
   ![image](https://github.com/KelvinIyalo/VPD-Money-Kelvin-Iyalo-Test/assets/53624560/852113ad-b874-4ab6-8507-bbd1c4598f9f)

   7. The Transaction History Screen, This show the list of transactions done on the device and locally cached into room database
![image](https://github.com/KelvinIyalo/VPD-Money-Kelvin-Iyalo-Test/assets/53624560/bfd28b4e-2328-4058-8783-ff684817f2f7)





