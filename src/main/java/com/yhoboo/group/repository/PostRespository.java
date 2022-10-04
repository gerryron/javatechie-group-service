package com.yhoboo.group.repository;

import com.yhoboo.group.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRespository extends JpaRepository<Post, Integer> {
}
