# Autonomous garden irrigation system based on the Internet of Things - WEB APP

Autonomous garden irrigation system based on the Internet of Things, web app. This is part of my Bachelors degree thesis as well
as my personal portfolio. This controller node's responsability is to receive the data from the controller node every 10 seconds, 
and send commands (decitions) to it, contains all logic, modules like users, spaces, nodes, devices, etc are configured there,
and the communication with the controller node is established by using WebSockets. It was developed by Luis Espinosa Llanos 
and it was used the following technologies and tools: 

<table style="width:100%">
  <tr>
    <td>
  	Core	
    </td>
    <td>
  	Java 1.8, Spring Framework, JDBCTemplate, Jackson Databinding, RestFul.
    </td>
  </tr>
  <tr>
    <td>
  	Server	
    </td>
    <td>
  	WildFly
    </td>
  </tr>
  <tr>
    <td>
  	Executable	
    </td>
    <td>
  	War
    </td>
  </tr>
</table>

## Video
A video exposing the functionality of the complete proyect.

https://youtu.be/R3nJAr5eoEk


## And the other modules???
You can find them here:

- Controller: https://github.com/LuisEspinosa7/-SRJA-controller-node
- Actuator: https://github.com/LuisEspinosa7/SRJA-actuator-node


## Pictures
Some pictures of the project on a local environment respectively:

<table style="width:100%">
  <tr>
    <td>
  		<img width="450" alt="Test" src="https://user-images.githubusercontent.com/56041525/166121655-ca312b34-e170-4e61-8ec4-4136e4af5136.PNG">
	  </td>
    <td>
  	<img width="450" alt="Web App" src="https://user-images.githubusercontent.com/56041525/166121628-899b15ec-f8ff-453b-94e7-ba7e9b030c35.PNG">
    </td>
  </tr>
</table>

<table style="width:100%">
  <tr>
    <td>
  		<img width="450" alt="Web App" src="https://user-images.githubusercontent.com/56041525/166121673-e97ba897-93eb-43fb-8da8-52a7eb093d7e.PNG">
	  </td>
    <td>
	<img width="450" alt="Web App" src="https://user-images.githubusercontent.com/56041525/166121685-a29a05a3-d206-409d-9303-879e627c1158.PNG">
    </td>
  </tr>
</table>


## Installation

This proyect should be installed using the following command:
```bash
mvn clean install
```

## Usage
Please deploy the war inside a wildfly server


## Scientific production
Be aware that this project was made after a process of formal research.

https://www.researchgate.net/publication/315793360_Sistema_de_Riego_Basado_En_La_Internet_De_Las_Cosas_IoT


## Contributing
This proyect is quite simple, and is part of my personal portfolio, so it is not intended to receive contributions.


## License
Protected by author's rights
