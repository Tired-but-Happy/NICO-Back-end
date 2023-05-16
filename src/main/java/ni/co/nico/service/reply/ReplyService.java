package ni.co.nico.service.reply;

public interface ReplyService {
    public void createReply(String contractAddress, Long boardId, String writerAddress, String content);
}
