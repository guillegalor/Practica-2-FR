import socket
import sys

# Creacion del socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('localhost', 8989)
print('Conectandose a %s puerto %s' % server_address)

sock.connect(server_address)


try:
    # Lectura de las opciones
    data = sock.recv(512)
    print(data.decode())

    # Send data
    option = input()
    sock.sendall(option.encode())

    # Respuesta del servidor / Si descomentas esta línea se ve el error
    # sock.recv(1)

    if True:
        print('Opción correcta')
    else:
        print('Opción incorrecta')

finally:
    print('Cerrando el socket')
    sock.close
