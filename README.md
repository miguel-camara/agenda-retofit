# Agenda en Android Studio con Retrofit

### Api utilizada

https://agenda-api-node.herokuapp.com/

La api fue hecha con node.js y use uso cloud firestore como base de datos.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/API.PNG" alt="API" width="200px"></p>

#### Node

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/Node.PNG" alt="Node" width="200px"></p>

#### Firestore

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/Firestore.PNG" alt="Firestore" width="200px"></p>


## Login

Al iniciar la aplicación encontraremos un login, para poder ingresar a pantalla principal es necesarios ingresar un correo y contraseña valido.
En caso de no contar con uno es necesario **registrarse.**

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/1.jpg" alt="Login" width="200px"></p>

-------------------------------

## Register

En el registro hay que llenar los siguientes campos **nombre**, **correo**, **contraseña** y **telefono.**

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/2.jpg" alt="Register" width="200px"></p>

En caso de que el correo exista mandara una alerta indicando que la cuenta ya existe.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/3.jpg" alt="Register" width="200px"></p>

En caso de no existir nos redireccionara al Login.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/4.jpg" alt="Register" width="200px"></p>

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/5.jpg" alt="Register" width="200px"></p>

-------------------------------

## Login Alerta

En caso de ingresar un correo o contraseña incorrectos nos lo indicara

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/6.png" alt="Login" width="200px"></p>

## Agenda

Al iniciar sesión nos mandara a la siguiente vista, en donde se listan los eventos o agendas que tengamos creado.
Para **crear** uno nuevo damos clic en el botón de la parte **inferior derecha**.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/7.jpg" alt="Agenda" width="200px"></p>

-------------------------------

## Agregar Agenda

Para agregar un evento es necesario seleccionar una **categoría**, **fecha** y **hora**.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/8.jpg" alt="Agregar Agenda" width="200px"></p>

-------------------------------

## Cambios en Agenda

Al agregar el evento o agenda aparecerá en la pantalla principal.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/9.png" alt="Cambios de agenda" width="200px"></p>

-------------------------------

## Múltiples Agenda

Podemos **agregar** tantos eventos o agendas deseemos.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/10.jpg" alt="Multiples agenda" width="200px"></p>

-------------------------------

## Agenda Favorita

Cada evento tiene dos botones uno de **favoritos** y otro para poder **editar** el evento o agenda.
En el caso de favoritos al dar clic sobre el icono este cambiara de **color** al volver a darle clic este volverá a su **estado original.**

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/11.jpg" alt="Agenda favorita" width="200px"></p>

-------------------------------

## Editar Agenda

Al dar clic en el evento que deseamos editar cambiara a la siguiente vista, en donde todos los cambios estarán llenos con la información con la cual guardamos el evento, podemos cambiar el valor de **uno** o **todos los campos.**

<p style = 'text-align:center;'> <img src="https://github.com/miguel-camara/agenda-retofit/blob/main/12.png" alt="Editar Agenda" width="200px"> </p>

-------------------------------

## Agenda Editada

Podemos ver los cambios después de editar el evento.

<p style = 'text-align:center;'> <img src="https://github.com/miguel-camara/agenda-retofit/blob/main/13.jpg" alt="Agenda Editada" width="200px"> </p>

-------------------------------

## Eliminar Agenda

Para poder eliminar un evento es necesario **mantener presionado** sobre el evento que deseemos eliminar, hasta que aparezca una **ventana emergente.**

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/14.jpg" alt="Eliminar Agenda" width="200px"></p>

-------------------------------

## Agenda Eliminada

Podemos ver que el evento se elimino.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/15.jpg" alt="Agenda Eliminada" width="200px"></p>

-------------------------------

## Nuevo Usuario

Podemos tener múltiples usuarios.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/16.jpg" alt="Login" width="200px"></p>

La agenda es por usuario por lo que cada usuarios ve **todos los eventos que a creado, así como agregar nuevos, editar y eliminar eventos, como tambien indicar eventos favoritos**.

<p style = 'text-align:center;'><img src="https://github.com/miguel-camara/agenda-retofit/blob/main/17.jpg" alt="Login" width="200px"></p>