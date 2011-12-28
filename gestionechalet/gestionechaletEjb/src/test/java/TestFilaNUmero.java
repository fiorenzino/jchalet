public class TestFilaNUmero {
	public static void main(String[] args) {
		String filaNumero = "1:22";
		String fila = filaNumero.substring(0, filaNumero.indexOf(":"));
		String numero = filaNumero.substring(filaNumero.indexOf(":") + 1);
		System.out.println(fila);
		System.out.println(numero);
	}
}
