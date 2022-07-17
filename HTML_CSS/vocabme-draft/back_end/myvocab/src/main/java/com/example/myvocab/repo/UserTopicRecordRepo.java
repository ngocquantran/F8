package com.example.myvocab.repo;

import com.example.myvocab.model.UserTopicRecord;
import com.example.myvocab.model.enummodel.LearningStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTopicRecordRepo extends JpaRepository<UserTopicRecord, Long> {
    Optional<UserTopicRecord> findByStageAndUserTopic_Id(LearningStage stage, Long id);

    List<UserTopicRecord> findByUserTopic_Id(Long id);

    @Query(
            value = "SELECT *  " +
                    " FROM user_topic_record utr " +
                    " INNER JOIN user_topic ut ON utr.id_user_topic=ut.id " +
                    " WHERE utr.stage ='NOW' AND ut.id_topic= ?1 "+
                    " ORDER BY utr.rightAnswers DESC, utr.testTime ASC, utr.id ASC"+
                    "  LIMIT 10",
            nativeQuery = true)
    List<UserTopicRecord> getTopTenOfUserTopic(Long topicId);



    @Query(
            value = "WITH rank_chart AS( " +
                                            " SELECT utr.id,utr.testTime ,utr.rightAnswers,u.id as user_id,u.userName ,ROW_NUMBER () OVER(PARTITION BY utr.id_user_topic ORDER BY utr.rightAnswers DESC,utr.testTime ASC,utr.id ASC ) as user_rank  " +
                                            " FROM user_topic_record utr " +
                                            " INNER JOIN user_topic ut ON utr.id_user_topic=ut.id " +
                                            " INNER JOIN user_course uc ON ut.id_user_course = uc.id " +
                                            " INNER JOIN users u ON uc.id_user = u.id " +
                                            " WHERE utr.stage ='NOW' AND ut.id_topic= ?1 )"  +
                    "            SELECT rc.user_rank " +
                    "            FROM rank_chart rc " +
                    "            WHERE rc.user_id =?2",
            nativeQuery = true)
    Optional<Integer> getUserTopicRank(Long topicId,String userId);

//    @Query(
//            value = "WITH rank_chart AS( " +
//                    "                    WITH user_group AS (SELECT *,ROW_NUMBER () OVER(PARTITION BY id_user_topic ORDER BY rightAnswers DESC,testTime ASC,id ASC ) AS row_no " +
//                    "                    FROM user_topic_record  " +
//                    "                    WHERE stage ='NOW' OR stage='BEST') " +
//                    "                    SELECT ROW_NUMBER() OVER(ORDER BY ug.id ASC) AS ranks, u.userName, u.id as user_id ,ug.rightAnswers,ug.testTime " +
//                    "                    FROM user_group ug " +
//                    "                    INNER JOIN user_topic ut ON ug.id_user_topic=ut.id " +
//                    "                    INNER JOIN user_course uc ON ut.id_user_course = uc.id " +
//                    "                    INNER JOIN users u ON uc.id_user = u.id " +
//                    "                    WHERE row_no =1 AND ut.id_topic= ?1) " +
//                    "            SELECT * " +
//                    "            FROM rank_chart rc " +
//                    "            WHERE rc.user_id =?2",
//            nativeQuery = true)
//    Optional<Integer> getUserTopicRank(Long topicId,String userId);

//    @Query(
//            value = "WITH user_group AS (" +
//                    " SELECT *,ROW_NUMBER () OVER(PARTITION BY id_user_topic ORDER BY rightAnswers DESC,testTime ASC,id ASC ) AS row_no " +
//                    " FROM user_topic_record utr " +
//                    " WHERE stage ='NOW' OR stage='BEST' " +
//                    ")SELECT *  " +
//                    " FROM user_group ug " +
//                    " INNER JOIN user_topic ut ON ug.id_user_topic=ut.id " +
//                    " WHERE row_no =1 AND ut.id_topic= ?1 "+
//                    "  LIMIT 10",
//            nativeQuery = true)
//    List<UserTopicRecord> getTopTenOfUserTopic(Long topicId);

}