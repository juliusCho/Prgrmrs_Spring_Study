package com.github.prgrms.socialserver.posts.model;

import com.github.prgrms.socialserver.global.model.IdVO;
import com.github.prgrms.socialserver.users.model.UserEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PostingRequestVO {

  private String contents;

  protected PostingRequestVO() {}

  public String getContents() {
    return contents;
  }

  public PostEntity newPost(IdVO<UserEntity, Long> userId, WriterVO writer) {
    return new PostEntity(userId, writer, contents);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("contents", contents)
      .toString();
  }

}