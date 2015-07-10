import org.apache.log4j.Logger;

public class LoggerTest {
	public static Logger logger = Logger.getLogger(LoggerTest.class);

	public static void main(String[] args) {
		logger.info("LoggerTest class!");
	}
}
