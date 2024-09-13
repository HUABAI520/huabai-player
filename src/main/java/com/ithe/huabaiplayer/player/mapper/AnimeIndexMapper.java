package com.ithe.huabaiplayer.player.mapper;

import com.ithe.huabaiplayer.player.model.entity.AnimeIndex;
import com.ithe.huabaiplayer.player.model.vo.AnimeIndexVo;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 动漫表 映射层。
 *
 * @author L
 * @since 2024-08-28
 */
@Mapper
public interface AnimeIndexMapper extends BaseMapper<AnimeIndex> {
    AnimeIndexVo getAnimeIndexVoById(Long id,Long userId);
    @Select({"SELECT * FROM anime_index ORDER BY RAND() LIMIT #{limit}"})
    List<AnimeIndexVo> selectRandomly(@Param("limit") int limit);
}
