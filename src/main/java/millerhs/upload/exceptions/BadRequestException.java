package millerhs.upload.exceptions;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -1609777532328390353L;

	public BadRequestException(String detail) {
        super(detail);
    }
}