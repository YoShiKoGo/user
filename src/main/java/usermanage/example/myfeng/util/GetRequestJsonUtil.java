package usermanage.example.myfeng.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 	解析request请求流，获取请求数据
 */
public class GetRequestJsonUtil {



	/**
	 * 描述:获取 post 请求的 byte[] 数组
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request)
			throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {

			int reade = request.getInputStream().read(buffer, i,
					contentLength - i);
			if (reade == -1) {
				break;
			}
			i += reade;
		}
		return buffer;
	}


}
