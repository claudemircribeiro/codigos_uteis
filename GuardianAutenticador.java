package com.dataprom.guardianautenticador;

import javax.xml.bind.DatatypeConverter;
import java.net.*;

class GuardianAutenticador {

	static StringBuilder msgHexa128Bytes = new StringBuilder();

	static{
		msgHexa128Bytes.append("0ED87AB2D06C65CF5BECAF5222B660AB");
		msgHexa128Bytes.append("BC040AF575F199B1BC040AF575F199B1");
		msgHexa128Bytes.append("BC040AF575F199B1BC040AF575F199B1");
		msgHexa128Bytes.append("BC040AF575F199B1BC040AF575F199B1");
		msgHexa128Bytes.append("BC040AF575F199B1BC040AF575F199B1");
		msgHexa128Bytes.append("BC040AF575F199B1BC040AF575F199B1");
		msgHexa128Bytes.append("BC040AF575F199B1BC040AF575F199B1");
		msgHexa128Bytes.append("BC040AF575F199B1BC040AF575F199B1");
	}

	public GuardianAutenticador() {
		super();
	}

	/**
	 * Método que converte um array de bytes
	 * em uma String concatenada com a representação
	 * de cada byte em hexa
	 *
	 * @param array
	 * @return String
	 */
	public static String toHexString(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}

	/**
	 * Método responsável por converter um array de bytes
	 * para uma String cocatenada com a representação hexa
	 * de cada byte do array
	 *
	 * @param stringEmHexa
	 * @return true
	 */
	public static byte[] toByteArray(String stringEmHexa) {
		return DatatypeConverter.parseHexBinary(stringEmHexa);
	}

	/**
	 * varifica retorno do serviço, os 16
	 * primeiros caracteres tem do retorno
	 * tem que serem iguais
	 *
	 * @param retorno
	 * @return
	 */
	public static boolean retornoUdpInvalido(String retorno) {
		if(retorno == null || retorno == "" ){
			return true;
		}
		return !msgHexa128Bytes.toString().substring(0,16).equalsIgnoreCase(retorno.substring(0,16));
	}

	public static void main(String args[]) throws Exception {
		DatagramSocket clientSocket = null;
		String ipServidor = args[0];
		int portaServidor = Integer.parseInt(args[1]);
		int qtdTentativas = Integer.parseInt(args[2]);
		int timeout = Integer.parseInt(args[3]);
		int qtdExecutadas = 0;

		try {

			InetAddress IPAddress = InetAddress.getByName(ipServidor);
			byte[] sendData = new byte[128];
			byte[] receiveData = new byte[2048];
			sendData = toByteArray(msgHexa128Bytes.toString());

			while(qtdExecutadas <= qtdTentativas) {

				clientSocket = new DatagramSocket();
				clientSocket.setSoTimeout(timeout);
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, portaServidor);

				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);

				try {

					clientSocket.send(sendPacket);
					clientSocket.receive(receivePacket);

					if(retornoUdpInvalido(toHexString(receivePacket.getData()))) {
						qtdExecutadas++;
					} else {
						break;
					}

				} catch (SocketTimeoutException e) {
					System.out.println("Timeout alcançado " + e);
					clientSocket.close();
					qtdExecutadas++;
				}
			}

		} catch (SocketException e1) {
			System.out.println("Socket fechado " + e1);
			clientSocket.close();
			qtdExecutadas++;
		}
		if(qtdExecutadas > qtdTentativas) {
			System.out.println("fracasso");
		} else {
			System.out.println("sucesso");
		}
	}
}