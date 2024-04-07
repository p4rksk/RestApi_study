package shop.mtcoding.blog.reply;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.user.SessionUser;
import shop.mtcoding.blog.user.User;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty
        private Integer boardId;
        @NotEmpty
        private String comment;

        public Reply toEntity(User user, Board board){

            return Reply.builder()
                    .comment(comment)
                    .board(board)
                    .user(user)
                    .build();
        }

    }
}
