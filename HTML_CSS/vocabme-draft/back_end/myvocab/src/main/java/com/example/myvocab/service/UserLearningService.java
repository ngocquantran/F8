package com.example.myvocab.service;

import com.example.myvocab.dto.UserTopicVocabDto;
import com.example.myvocab.dto.VocabTest;
import com.example.myvocab.dto.VocabsForChoosing;
import com.example.myvocab.exception.BadRequestException;
import com.example.myvocab.mapper.UserTopicVocabMapper;
import com.example.myvocab.mapper.VocabTestMapper;
import com.example.myvocab.model.*;
import com.example.myvocab.model.enummodel.LearningStage;
import com.example.myvocab.model.enummodel.TopicState;
import com.example.myvocab.repo.*;
import com.example.myvocab.request.FilterVocabRequest;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
    public void initUserTopicVocabs(Long topicId, String userId, LearningStage stage) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);
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
        UserTopic userTopic = isUserTopicExist(topicId, userId);
        return userTopicVocabRepo.getListOfUserTopicVocabDto(userTopic.getId(), stage);
    }


    //    Lưu list từ vựng muốn học của topicUser sau khi chọn xong
    public void updateUserTopicVocabAfterChooseWordToLearn(Long topicId, String userId, LearningStage stage, List<UserTopicVocabDto> requests) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);

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

    public List<Vocab> getListOfVocabByTopicToLearn(Long topicId, String userId) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);
        List<UserTopicVocab> listLearnNow = userTopicVocabRepo.findByUserTopic_IdAndLearningStage(userTopic.getId(), LearningStage.NOW);
        if (listLearnNow.isEmpty()) {
            List<UserTopicVocab> listLearnFirst = userTopicVocabRepo.findByUserTopic_IdAndLearningStage(userTopic.getId(), LearningStage.FIRST);
            return listLearnFirst.stream().filter(vocab -> vocab.isLearn()).map(v -> v.getVocab()).collect(Collectors.toList());
        } else {
            return listLearnNow.stream().filter(vocab -> vocab.isLearn()).map(v -> v.getVocab()).collect(Collectors.toList());
        }
    }


//USER TESTING SERVICE----------------------------------------------------------------------------------------------------------------------------------


    //    Lấy danh sách từ vựng trong topic để render bài test

    public List<Sentence> getListOfSentenceForTest(Long topicId) {
        Topic topic = isTopicExist(topicId);
        return topic.getSentences().stream().toList();
    }


    public List<VocabTest> getTestVocabs(Long topicId) {
        Topic topic = isTopicExist(topicId);

        List<VocabTest> words = topic.getVocabs().stream().toList()
                .stream()
                .map(vocab -> VocabTestMapper.toVocabTest(vocab))
                .map(vocabTest -> renderVocabAnswers(vocabTest, topic.getCourse().getId()))
                .map(vocabTest -> renderVnMeaningAnswers(vocabTest, topic.getCourse().getId()))
                .map(vocabTest -> renderEnSentenceAnswers(vocabTest, topic.getCourse().getId()))
                .collect(Collectors.toList());

        return words;
    }


//    Render answer list for VocabTest (Choosing 4 options)

    public VocabTest renderVocabAnswers(VocabTest vocabTest, Long courseId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> vocabs = getCourseVocabs(courseId);

        List<String> wordLists = new ArrayList<>();
        int index = 0;
        boolean isContinue = true;
        Random rd = new Random();
        while (isContinue) {
            if (index + 1 == answerIndex) {
                wordLists.add(vocabTest.getWord());
                index++;
            } else {
                int i = rd.nextInt(vocabs.size());
                if (!vocabs.get(i).equals(vocabTest.getWord()) & !wordLists.contains(vocabs.get(i))) {
                    wordLists.add(vocabs.get(i));
                    index++;
                }
            }
            if (index >= 4) {
                isContinue = false;
            }
        }
        vocabTest.setVocabs(wordLists);
        return vocabTest;
    }


    public VocabTest renderVnMeaningAnswers(VocabTest vocabTest, Long courseId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> vnMeanings = getCourseVnMeaning(courseId);
        List<String> vnLists = new ArrayList<>();
        int index = 0;
        boolean isContinue = true;
        Random rd = new Random();
        while (isContinue) {
            if (index + 1 == answerIndex) {
                vnLists.add(vocabTest.getVnMeaning());
                index++;
            } else {
                int i = rd.nextInt(vnMeanings.size());
                if (!vnMeanings.get(i).equals(vocabTest.getVnMeaning()) & !vnLists.contains(vnMeanings.get(i))) {
                    vnLists.add(vnMeanings.get(i));
                    index++;
                }
            }
            if (index >= 4) {
                isContinue = false;
            }
        }

        vocabTest.setVnMeanings(vnLists);
        return vocabTest;
    }

    public VocabTest renderEnSentenceAnswers(VocabTest vocabTest, Long courseId) {
        int answerIndex = vocabTest.getAnswerIndex();
        List<String> enSentences = getCourseEnSentence(courseId);
        List<String> enLists = new ArrayList<>();
        int index = 0;
        boolean isContinue = true;
        Random rd = new Random();
        while (isContinue) {
            if (index + 1 == answerIndex) {
                enLists.add(vocabTest.getEnSentence());
                index++;
            } else {
                int i = rd.nextInt(enSentences.size());
                if (!enSentences.get(i).equals(vocabTest.getEnSentence()) & !enLists.contains(enSentences.get(i))) {
                    enLists.add(enSentences.get(i));
                    index++;
                }
            }
            if (index >= 4) {
                isContinue = false;
            }
        }
        vocabTest.setEnSentences(enLists);
        return vocabTest;
    }


    //    Lấy kết quả Test client gửi về và xử lý, lưu vào database
    public void handleVocabTestResult(Long topicId, String userId, List<UserTopicVocabDto> requests) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);

        long totalNumber = userTopicVocabRepo.countDistinctByUserTopic_Id(userTopic.getId());
        long passVocabs = requests.stream().filter(v -> v.isStatus()).count();

        if (passVocabs / totalNumber > 0.9) {
            userTopic.setStatus(TopicState.PASS);                //Sau khi làm bài test, nếu trả lời đúng trên 90% thì PASS không thì CONTINUE
            openNextUserTopic(userTopic);
        } else {
            userTopic.setStatus(TopicState.CONTINUE);
        }
        userTopicRepo.save(userTopic);

        //Kiểm tra có phải lần test đầu tiên không bằng cách check chiều dài List UsertopicVocab stage NOW
        List<UserTopicVocab> checkList=userTopicVocabRepo.findByUserTopic_IdAndLearningStage(userTopic.getId(),LearningStage.NOW);
        if (checkList.isEmpty()){                                                          //Không có, sẽ khởi tạo lần đầu
            initUserTopicVocabs(topicId,userId, LearningStage.NOW);
        }else {                                                                            // Nếu có rồi, cần cập nhật trạng thái Best và Previous trước khi lưu kết quả mới
            initUserTopicVocabs(topicId,userId,LearningStage.BEST);
            initUserTopicVocabs(topicId,userId,LearningStage.PREVIOUS);
        }


        for (UserTopicVocabDto v : requests) {
            Optional<UserTopicVocab> o_vocab = userTopicVocabRepo.findByUserTopic_IdAndVocab_IdAndLearningStage(userTopic.getId(), v.getId(), LearningStage.NOW);
            if (o_vocab.isEmpty()) {
                throw new BadRequestException("Không tìm thấy từ vựng có Id = " + v.getId() + " trong danh sách Từ vựng của topic User giai đoạn NOW");
            }
            UserTopicVocab vocab = o_vocab.get();
            vocab.setStatus(v.isStatus());
            vocab.setTestTime(v.getTestTime());
            userTopicVocabRepo.save(vocab);
        }
    }


    // Set userTopic tiếp theo ở trạng thái NOW nếu pass topic trước
    public void openNextUserTopic(UserTopic userTopic) {
        UserCourse userCourse = userTopic.getUserCourse();
        List<UserTopic> userTopicList = userTopicRepo.findByUserCourse_Id(userCourse.getId());
        for (int i = userTopicList.size() - 2; i >= 0; i--) {
            if (userTopicList.get(i).getStatus() == TopicState.PASS) {
                UserTopic userTopicNext = userTopicList.get(i + 1);
                userTopicNext.setStatus(TopicState.NOW);
                userTopicRepo.save(userTopicNext);
            }
        }


    }


    // Lấy kết quả Test hiện tại từ database
    public List<UserTopicVocab> getTestResultNowStage(Long topicId, String userId) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);
        return userTopicVocabRepo.findByUserTopic_IdAndLearningStage(userTopic.getId(), LearningStage.NOW);
    }


    //    Lấy list đặc tính của Vocab theo course để render ra đáp án bất kỳ
    public List<String> getCourseVocabs(Long courseId) {
        return vocabRepo.findByTopics_Course_Id(courseId).stream().map(Vocab::getWord).collect(Collectors.toList());
    }

    public List<String> getCourseVnMeaning(Long courseId) {
        return vocabRepo.findByTopics_Course_Id(courseId).stream().map(Vocab::getVnMeaning).collect(Collectors.toList());
    }

    public List<String> getCourseEnSentence(Long courseId) {
        return vocabRepo.findByTopics_Course_Id(courseId).stream().map(Vocab::getEnSentence).collect(Collectors.toList());
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

    public UserTopic isUserTopicExist(Long topicId, String userId) {
        Optional<UserTopic> o_userTopic = userTopicRepo.findByTopic_IdAndUserCourse_User_Id(topicId, userId);
        if (o_userTopic.isEmpty()) {
            throw new BadRequestException("Không tìm thấy UserTopic");
        }
        return o_userTopic.get();
    }


}
