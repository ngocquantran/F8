package com.example.myvocab.service;

import com.example.myvocab.dto.UserTopicVocabDto;
import com.example.myvocab.dto.VocabsForChoosing;
import com.example.myvocab.exception.BadRequestException;
import com.example.myvocab.mapper.UserTopicVocabMapper;
import com.example.myvocab.model.*;
import com.example.myvocab.model.enummodel.LearningStage;
import com.example.myvocab.model.enummodel.TopicState;
import com.example.myvocab.repo.*;
import com.example.myvocab.request.FilterVocabRequest;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLearningService {
    @Autowired
    UserTopicVocabMapper mapper;
    @Autowired
    private UserCourseRepo userCourseRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private UserTopicRepo userTopicRepo;
    @Autowired
    private VocabRepo vocabRepo;
    @Autowired
    private UserTopicVocabRepo userTopicVocabRepo;

    public Course findCourseByTopicId(Long topicId) {
        Topic topic = isTopicExist(topicId);
        return topic.getCourse();
    }

    public UserCourse createUserCourse(Long courseId, String userId) {
        Users user = isUserExist(userId);
        Course course = isCourseExist(courseId);

        Optional<UserCourse> o_userCourse = userCourseRepo.findByCourse_IdAndUser_Id(courseId, userId);  //Kiểm tra UserCourse tồn tại không
        if (o_userCourse.isPresent()) {
            return o_userCourse.get();
        }
        UserCourse userCourse = UserCourse.builder()
                .course(course)
                .user(user)
                .build();
        userCourseRepo.save(userCourse);

        return userCourse;
    }

    public UserTopic createUserTopicWithStatus(Long topicId, String userId, TopicState topicState) {
        Topic topic = isTopicExist(topicId);

        Course course = findCourseByTopicId(topicId);
        UserCourse userCourse = createUserCourse(course.getId(), userId);  //Kiểm tra UserCourse, nếu đã tồn tại không chạy đến phần tạo mới


        Optional<UserTopic> o_userTopic = userTopicRepo.findByTopic_IdAndUserCourse_User_IdAndStatus(topicId, userId, topicState);  //Kiểm tra UserTopic đã tồn tại chưa
        if (o_userTopic.isPresent()) {
            return o_userTopic.get();
        }
        UserTopic userTopic = UserTopic.builder()
                .status(topicState)
                .topic(topic)
                .userCourse(userCourse)
                .build();
        userTopicRepo.save(userTopic);

        return userTopic;
    }


    //Khởi tạo từ vựng ứng với topic cho User cụ thể ứng với giai đoạn  của topic đó
    public void initUserTopicVocabs(Long topicId,String userId, LearningStage stage) {
        UserTopic userTopic=isUserTopicExist(topicId,userId);
        List<Vocab> vocabs = vocabRepo.findByTopics_Id(userTopic.getTopic().getId());
        for (Vocab v : vocabs) {
            boolean isUserTopicVocabExist = userTopicVocabRepo.existsByUserTopic_IdAndVocab_IdAndLearningStage(userTopic.getId(), v.getId(), stage);
            if (isUserTopicVocabExist) {
                return;
            } else {
                UserTopicVocab userTopicVocab = UserTopicVocab.builder()
                        .learningStage(stage)
                        .userTopic(userTopic)
                        .vocab(v)
                        .build();
                userTopicVocabRepo.save(userTopicVocab);
            }
        }
    }

    public void handleUserTopicVocabsWithStage(Long topicId, String userId, TopicState topicState, LearningStage stage, List<FilterVocabRequest> vocabRequestList) {
        Optional<UserTopic> o_userTopic = userTopicRepo.findByTopic_IdAndUserCourse_User_IdAndStatus(topicId, userId, topicState);
        if (o_userTopic.isEmpty()) {
            throw new BadRequestException("Chưa khởi tạo UserTopic");
        }
        UserTopic userTopic = o_userTopic.get();

        for (FilterVocabRequest v : vocabRequestList) {
            updateUserTopicVocab(userTopic, stage, v);
            System.out.println(v);

        }
    }

    // Cập nhật list từ vựng ứng với topic theo yêu cầu từ client
    public UserTopicVocab updateUserTopicVocab(UserTopic userTopic, LearningStage stage, FilterVocabRequest vocabRequest) {
        Optional<UserTopicVocab> o_topicVocab = userTopicVocabRepo.findByUserTopic_IdAndVocab_IdAndLearningStage(userTopic.getId(), vocabRequest.getVocabId(), stage);
        if (!o_topicVocab.isPresent()) {
            throw new BadRequestException("Không tồn tại từ vựng có id = " + vocabRequest.getVocabId() + " trong topic này");
        }

        UserTopicVocab vocab = o_topicVocab.get();
        System.out.println(vocab);
        vocab.setStatus(vocabRequest.isKnown());

        userTopicVocabRepo.save(vocab);
        System.out.println(vocab);
        return vocab;
    }

    //Lấy list từ vựng của topic sau khi Filter lần đầu, để chọn từ muốn học
//    public List<VocabsForChoosing> getListOfVocabsForChoosing(Long topicId,String userId,LearningStage stage){
//        Optional<UserTopic> o_userTopic = userTopicRepo.findByTopic_IdAndUserCourse_User_Id(topicId,userId);
//        if (o_userTopic.isEmpty()) {
//            throw new BadRequestException("Không tìm thấy UserTopic");
//        }
//        UserTopic userTopic = o_userTopic.get();
//        return userTopicVocabRepo.getVocabsForChoosing(userTopic.getId(),stage);
//    }

    //Lấy list từ vựng của topic sau khi Filter lần đầu, để chọn từ muốn học
    public List<UserTopicVocabDto> getListOfUserTopicVocabDto(Long topicId, String userId, LearningStage stage) {
        UserTopic userTopic=isUserTopicExist(topicId,userId);
        return userTopicVocabRepo.getListOfUserTopicVocabDto(userTopic.getId(), stage);
    }


    //    Lưu list từ vựng muốn học của topicUser sau khi chọn xong
    public void updateUserTopicVocabAfterChooseWordToLearn(Long topicId, String userId, LearningStage stage, List<UserTopicVocabDto> requests) {
        UserTopic userTopic=isUserTopicExist(topicId,userId);

        for (UserTopicVocabDto v : requests) {
            Optional<UserTopicVocab> o_userTopicVocab = userTopicVocabRepo.findById(v.getId());
            if (o_userTopicVocab.isEmpty()) {
                throw new BadRequestException("Không tìm thấy Từ vựng-topic có id = " + v.getId() + " của user có id = " + userId);
            }
            UserTopicVocab userTopicVocab = o_userTopicVocab.get();

            System.out.println(v);

            mapper.updateUserTopicVocabFromDto(v, userTopicVocab);

            userTopicVocabRepo.save(userTopicVocab);
        }
    }


//    Lấy danh sách từ vựng muốn học từ database để học

    public List<Vocab> getListOfVocabByTopicToLearn(Long topicId,String userId){
        UserTopic userTopic=isUserTopicExist(topicId,userId);
        List<UserTopicVocab> listLearnNow=userTopicVocabRepo.findByUserTopic_IdAndLearningStage(userTopic.getId(), LearningStage.NOW);
        if (listLearnNow.isEmpty()){
            List<UserTopicVocab> listLearnFirst= userTopicVocabRepo.findByUserTopic_IdAndLearningStage(userTopic.getId(), LearningStage.FIRST);
           return listLearnFirst.stream().filter(vocab-> vocab.isLearn()).map(v->v.getVocab()).collect(Collectors.toList());
        }else {
            return listLearnNow.stream().filter(vocab-> vocab.isLearn()).map(v->v.getVocab()).collect(Collectors.toList());
        }
    }





//    Helper Class

    public Topic isTopicExist(Long topicId) {
        Optional<Topic> o_topic = topicRepo.findTopicById(topicId);                     // Kiểm tra topicID tồn tại không
        if (!o_topic.isPresent()) {
            throw new BadRequestException("Không tồn tại topic có Id = " + topicId);
        }
        return o_topic.get();
    }

    public Users isUserExist(String userId) {
        Optional<Users> o_user = usersRepo.findById(userId);                             // Kiểm tra userId tồn tại không
        if (!o_user.isPresent()) {
            throw new BadRequestException("Không tồn tại user có Id = " + userId);
        }
        return o_user.get();
    }

    public Course isCourseExist(Long courseId) {
        Optional<Course> o_course = courseRepo.findCourseById(courseId);       // Kiểm tra courseId tồn tại không
        if (!o_course.isPresent()) {
            throw new BadRequestException("Không tồn tại course có Id = " + courseId);
        }
        return o_course.get();
    }

    public Vocab isVocabExist(Long vocabId) {
        Optional<Vocab> o_vocab = vocabRepo.findById(vocabId);
        if (o_vocab.isEmpty()) {
            throw new BadRequestException("Không tồn tại từ vựng có Id = " + vocabId);
        }
        return o_vocab.get();
    }

    public UserTopic isUserTopicExist(Long topicId,String userId){
        Optional<UserTopic> o_userTopic = userTopicRepo.findByTopic_IdAndUserCourse_User_Id(topicId, userId);
        if (o_userTopic.isEmpty()) {
            throw new BadRequestException("Không tìm thấy UserTopic");
        }
        return o_userTopic.get();
    }



}
