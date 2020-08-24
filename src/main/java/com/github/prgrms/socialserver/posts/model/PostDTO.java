package com.github.prgrms.socialserver.posts.model;

import com.github.prgrms.socialserver.global.utils.DateUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

import static org.springframework.beans.BeanUtils.copyProperties;

public class PostDTO {

  private Long seq;

  private String contents;

  private int likes;

  private boolean likesOfMe;

  private int comments;

  private WriterVO writer;

  private LocalDateTime createAt;

  private String createDt;

  public PostDTO(PostEntity source) {
    copyProperties(source, this);

    this.writer = source.getWriter().orElse(null);
  }

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public boolean isLikesOfMe() {
    return likesOfMe;
  }

  public void setLikesOfMe(boolean likesOfMe) {
    this.likesOfMe = likesOfMe;
  }

  public int getComments() {
    return comments;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public WriterVO getWriter() {
    return writer;
  }

  public void setWriter(WriterVO writer) {
    this.writer = writer;
  }

  public LocalDateTime getCreateAt() {
    return DateUtil.convertToUTCDate(createDt);
  }

  public void setCreateAt(LocalDateTime createAt) {
    this.createAt = createAt;
  }

  public String getCreateDt() {
    return this.createDt;
  }

  public void setCreateDt(final String createDt) {
    this.createDt = createDt;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("contents", contents)
      .append("likes", likes)
      .append("likesOfMe", likesOfMe)
      .append("comments", comments)
      .append("writer", writer)
      .append("createAt", createAt)
      .append("createDt", createDt)
      .toString();
  }

}