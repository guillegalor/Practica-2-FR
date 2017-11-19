import socket
import sys

# Creamos el socket TCP
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('localhost', 8989)
print('Iniciando servidor en %s puerto %s' % server_address)

# Enlazamos el socket a la direccion definida
sock.bind(server_address)

sock.listen(1)

while True:
    print('esperando a un cliente...')
    connection, client_address = sock.accept()

    try:
        print('conexion desde', client_address)

        # Envias el menú
        connection.sendall('Menú de opciones:\n-Registrarse(1) \n-Encriptar(2) \n-Desencriptar(3)')

        #Lectura del la opción
        while True:
            data = connection.recv(4)
            global msg_received
            msg_received += data
            if not data:
                print('mensaje completo recibido')
                break

        option = int(msg_received)
        if 1 < option < 3:
            connection.sendall(1)       # 1 opción correcta
        else:
            connection.sendall(0)       # 0 opción incorrecta
            continue

        if option == 1:
            var = 1 #Registrarse
        elif option == 2:
            var = 2 #Encriptar
        elif option == 3:
            var = 3 #Desencriptar

    finally:
        connection.close
