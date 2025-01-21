package db;
/*Foi criado uma classe com uma excessao personalizada que quando de erro no bancode dados
 * 
 */
public class DbException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DbException(String msg ) {
		super(msg);
	}
}
