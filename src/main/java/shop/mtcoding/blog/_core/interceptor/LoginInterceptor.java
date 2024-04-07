package shop.mtcoding.blog._core.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception500;
import shop.mtcoding.blog._core.utils.JwtUtil;
import shop.mtcoding.blog.user.SessionUser;

public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //Bearer jwt토근
        String jwt = request.getHeader("Autorization");

        if (jwt == null) { //필터에서 하면 어렵다  그래서 인터셉터에서 한다.
            throw new Exception401("jwt 토큰을 전달해주세요");
        }

        jwt = jwt.replace("Bearer ", "");


        //검증
        try {
            SessionUser sessionUser = JwtUtil.verify(jwt);

            //임시 세션 (jessionId는 필요 없다)
            HttpSession session = request.getSession();
            session.setAttribute("sessionUser", sessionUser);
            return true;
        } catch (JWTDecodeException e) {
            throw new Exception401("토큰이 유효하지 않습니다");
        } catch (Exception e) {
            throw new Exception500(e.getMessage());
        }
    }
}
