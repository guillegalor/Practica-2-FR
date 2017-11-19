import socket
import sys

# Creación el socket TCP
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
        connection.sendall('Menú de opciones:\n-Registrarse(1) \n-Encriptar(2) \n-Desencriptar(3)'.encode())

        # Lectura del la opción
        msg_received = ''
        while True:
            data = connection.recv(4)
            if not data:
                print('mensaje completo recibido')
                break
            msg_received += data.decode()

        option = int(msg_received)
        if 1 <= option <= 3:
            print('opcion correcta')
            connection.sendall('1'.encode())       # 1 opción correcta
        else:
            print('opcion incorrecta')
            connection.sendall('0'.encode())       # 0 opción incorrecta
            continue

        if option == 1:
            var = 1 #Registrarse
        elif option == 2:
            var = 2 #Encriptar
        elif option == 3:
            var = 3 #Desencriptar

    finally:
        connection.close
