package com.sist.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sist.mapper.MovieMapper;
import com.sist.vo.AudienceEvaluationTrendVO;
import com.sist.vo.CelebVO;
import com.sist.vo.GenreVO;
import com.sist.vo.MovieDetailVO;
import com.sist.vo.MovieJoinVO;
import com.sist.vo.MoviePicturesVO;
import com.sist.vo.MovieReviewVO;
import com.sist.vo.NetizenEvaluationTrendVO;
import com.sist.vo.NewsReviewVO;
import com.sist.vo.SpecialPointVO;
import com.sist.vo.WatchingTrendVO;

@Repository
public class MovieDAO {
	@Autowired
	private MovieMapper mapper; 
	
	public MovieDetailVO getMovieDetailData(int movie_id)
	{
	   return mapper.getMovieDetailData(movie_id);
	}

	public WatchingTrendVO getWatchingTrend(int movie_id)
	{
		return mapper.getWatchingTrend(movie_id);
	}
	public List<String> getMoviePictures(int movie_id)
	{
		return mapper.getMoviePictures(movie_id);
	}
	public List<String> getMovieUrl(int movie_id)
	{
		return mapper.getMovieUrl(movie_id);
	}
	public List<CelebVO> getDirectorData(int movie_id){
		return mapper.getDirectorData(movie_id);
	}
	
	public List<CelebVO> getActorData(int movie_id){
		return mapper.getActorData(movie_id);
	}
	
	public List<String> getGenreData(int movie_id){
		return mapper.getGenreData(movie_id);
	}
	
	public List<MovieDetailVO> getMovieList(Map map){
		return mapper.getMovieList(map);
	}
	
	public int getTotalPage(Map map){
		return mapper.getTotalPage(map);
	}
	
	public List<MovieReviewVO> movieReviewData(int movie_id){
		return mapper.movieReviewData(movie_id);
	}
	
	public MovieReviewVO movieReviewSelect(int pno)
	{
		return mapper.movieReviewSelect(pno);
	}
	
	public int movieTotalReview(int movie_id)
	{
		return mapper.movieTotalReview(movie_id);
	}
	
	public void movieReviewInsert(MovieReviewVO vo){
		mapper.movieReviewInsert(vo);
	}
	public void movieReviewUpdate(MovieReviewVO vo){
		mapper.movieReviewUpdate(vo);
	}
	public void movieReviewDelete(int no){
		mapper.movieReviewDelete(no);
	}
	public AudienceEvaluationTrendVO getAudienceEvaluationTrend(int movie_id)
	{
	   return mapper.getAudienceEvaluationTrend(movie_id);
	}
	public NetizenEvaluationTrendVO getNetizenEvaluationTrend(int movie_id)
	{
	   return mapper.getNetizenEvaluationTrend(movie_id);
	}
	
	public List<MovieDetailVO> getSameDirector(int movie_id){
		return mapper.getSameDirector(movie_id);
	}
	public List<CelebVO> getRelActor(int movie_id){
		return mapper.getRelActor(movie_id);
	}
	
	public List<Integer> getRelMovieId(int movie_id){
		return mapper.getRelMovieId(movie_id);
	}
	
	public int movieTotalSameDirector(int movie_id){
		return mapper.movieTotalSameDirector(movie_id);
	}
}	
