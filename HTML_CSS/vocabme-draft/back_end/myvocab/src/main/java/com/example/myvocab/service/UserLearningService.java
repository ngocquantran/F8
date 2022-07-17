package com.example.myvocab.service;

import com.example.myvocab.dto.*;
import com.example.myvocab.exception.BadRequestException;
import com.example.myvocab.exception.NotFoundException;
import com.example.myvocab.mapper.VocabTestMapper;
import com.example.myvocab.model.*;
import com.example.myvocab.model.enummodel.Gender;
import com.example.myvocab.model.enummodel.LearningStage;
import com.example.myvocab.model.enummodel.TopicState;
import com.example.myvocab.repo.*;
import com.example.myvocab.request.FilterVocabRequest;
import com.example.myvocab.request.LearnVocabRequest;
import com.example.myvocab.request.TestVocabResultRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserLearningService {

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
    @Autowired
    private UserTopicRecordRepo userTopicRecordRepo;

    @Autowired private ModelMapper modelMapper;
    @Autowired private SentenceRepo sentenceRepo;
    @Autowired private ContextRepo contextRepo;


    public UserCourse getUserCourse(Long courseId, String userId){
        Optional<UserCourse> o_userCourse=userCourseRepo.findByCourse_IdAndUser_Id(courseId, userId);
        if (o_userCourse.isEmpty()){
            throw new NotFoundException("Không tìm thấy UserCourse");
        }
        return o_userCourse.get();
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

    public UserTopic createPendingUserTopic(Long topicId, String userId) {
        Topic topic = isTopicExist(topicId);
        Course course = topic.getCourse();
        UserCourse userCourse = createUserCourse(course.getId(), userId);  //Kiểm tra UserCourse, nếu đã tồn tại không chạy đến phần tạo mới


        Optional<UserTopic> o_userTopic = userTopicRepo.findByTopic_IdAndUserCourse_User_Id(topicId, userId, UserTopic.class);  //Kiểm tra UserTopic đã tồn tại chưa
        if (o_userTopic.isPresent()) {
            UserTopic userTopic=o_userTopic.get();
            userTopic.setStatus(TopicState.PENDING);
            userTopicRepo.save(userTopic);
            return userTopic;
        }
        UserTopic userTopic = UserTopic.builder()  //Nếu UserTopic chưa tồn tại thì tạo mới ở trạng thái pending
                .status(TopicState.PENDING)
                .topic(topic)
                .userCourse(userCourse)
                .build();
        userTopicRepo.save(userTopic);

        return userTopic;
    }


    public UserTopicRecord createUserTopicRecordByStage(Long topicId, String userId, LearningStage stage) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);

        Optional<UserTopicRecord> o_userTopicRecord = userTopicRecordRepo.findByStageAndUserTopic_Id(stage, userTopic.getId());
        if (o_userTopicRecord.isPresent()) {
            return o_userTopicRecord.get();
        }
        UserTopicRecord userTopicRecord = UserTopicRecord.builder()
                .testTime(0)
                .rightAnswers(0)
                .stage(stage)
                .userTopic(userTopic)
                .build();
        userTopicRecordRepo.save(userTopicRecord);
        return userTopicRecord;
    }


    public void initUserTopicVocabs(Long topicId, String userId) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);
        List<Vocab> vocabs = vocabRepo.findByTopics_Id(topicId);
        System.out.println("Lỗi ở đây---------------------------------------------------");
        for (Vocab v : vocabs) {
            Optional<UserTopicVocab> o_userTopicVocab = userTopicVocabRepo.findByUserTopic_IdAndVocab_Id(userTopic.getId(), v.getId());
            if (o_userTopicVocab.isEmpty()) {
                System.out.println("Lỗi 1-------------------------------------------------------------");
                UserTopicVocab userTopicVocab = UserTopicVocab.builder()
                        .userTopic(userTopic)
                        .vocab(v)
                        .build();
                userTopicVocabRepo.save(userTopicVocab);
            }
        }
    }

    public void handleSubmittedFilterVocabResult(Long topicId, String userId, List<FilterVocabRequest> requests) {
        UserTopic userTopic = createPendingUserTopic(topicId, userId);                                        //Khởi tạo userTopic và UserCourse
        UserTopicRecord userTopicRecord = createUserTopicRecordByStage(topicId, userId, LearningStage.FIRST);   // Khởi tạo userTopic Record ở giai đoạn First
        initUserTopicVocabs(topicId, userId);                                                               //Khởi tạo List UserTopicVocab

        Long numberOfRightAnswers = requests.stream().filter(vocabRequest -> vocabRequest.isStatus()).count();
        userTopicRecord.setRightAnswers(numberOfRightAnswers.intValue());                                   // Lưu số từ vựng đã biết cho record First

        System.out.println(requests);


        //Update trạng thái từ vựng từ request vào usertopicvocab tương ứng
        for (FilterVocabRequest v : requests) {
            System.out.println(v);
            Optional<UserTopicVocab> o_userTopicVocab = userTopicVocabRepo.findByUserTopic_IdAndVocab_Id(userTopic.getId(), v.getVocabId());
            if (o_userTopicVocab.isEmpty()) {
                throw new NotFoundException("Không tìm thấy từ vựng có id = " + v.getVocabId() + " trong topic có id = " + topicId);
            }
            UserTopicVocab userTopicVocab = o_userTopicVocab.get();
            System.out.println(userTopicVocab);
            userTopicVocab.setStatus(v.isStatus());
            userTopicVocabRepo.save(userTopicVocab);
        }

    }


    public List<ChooseVocabDto> getTopicVocabToChoose(Long topicId, String userId) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);
        return userTopicVocabRepo.getTopicVocabsToChoose(userTopic.getId());
    }

    //    Lưu list từ vựng muốn học của topicUser sau khi chọn xong
    public void saveLearnRequestToUserTopicVocab(Long topicId, String userId, List<LearnVocabRequest> requests) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);

        for (LearnVocabRequest v : requests) {
            Optional<UserTopicVocab> o_userTopicVocab = userTopicVocabRepo.findByUserTopic_IdAndVocab_Id(userTopic.getId(), v.getVocabId());
            if (o_userTopicVocab.isEmpty()) {
                throw new NotFoundException("Không tìm thấy từ vựng có id = " + v.getVocabId() + " trong topic có id = " + topicId);
            }
            UserTopicVocab userTopicVocab = o_userTopicVocab.get();
            userTopicVocab.setLearn(v.isLearn());
            userTopicVocabRepo.save(userTopicVocab);
        }
    }

    //    Lấy danh sách từ vựng muốn học từ database để học
    public List<Vocab> getTopicVocabsToLearn(Long topicId, String userId) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);
        List<Vocab> vocabs = userTopicVocabRepo.findByUserTopic_IdAndLearn(userTopic.getId(), true);
        return vocabs;
    }


//USER TESTING SERVICE----------------------------------------------------------------------------------------------------------------------------------


    //    Lấy danh sách từ vựng trong topic để render bài test

    public List<Sentence> getListOfSentenceForTest(Long topicId) {
        Topic topic = isTopicExist(topicId);
        return topic.getSentences().stream().toList();
    }


    public List<VocabTestDto> getTestVocabs(Long topicId) {
        Topic topic = isTopicExist(topicId);

        List<VocabTestDto> words = topic.getVocabs().stream().toList()
                .stream()
                .map(vocab -> VocabTestMapper.toVocabTest(vocab))
                .map(vocabTest -> renderVocabAnswers(vocabTest, topic.getCourse().getId()))
                .map(vocabTest -> renderVnMeaningAnswers(vocabTest, topic.getCourse().getId()))
                .map(vocabTest -> renderEnSentenceAnswers(vocabTest, topic.getCourse().getId()))
                .collect(Collectors.toList());

        Collections.shuffle(words);
        return words;
    }


//    Render answer list for VocabTest (Choosing 4 options)

    public VocabTestDto renderVocabAnswers(VocabTestDto vocabTest, Long courseId) {
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


    public VocabTestDto renderVnMeaningAnswers(VocabTestDto vocabTest, Long courseId) {
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

    public VocabTestDto renderEnSentenceAnswers(VocabTestDto vocabTest, Long courseId) {
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


    //    Lấy kết quả Test client gửi về và xử lý, lưu vào database
    public void handleVocabTestResult(Long topicId, String userId, List<TestVocabResultRequest> requests) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);

        //Kiểm tra có phải lần test đầu tiên không bằng cách check UsertopicRecord stage NOW
        Optional<UserTopicRecord> checkList = userTopicRecordRepo.findByStageAndUserTopic_Id(LearningStage.NOW, userTopic.getId());
        if (checkList.isEmpty()) {                                                          //Không có, sẽ khởi tạo lần đầu
            UserTopicRecord recordNow = createUserTopicRecordByStage(topicId, userId, LearningStage.NOW);

            updateTestVocabResult(userTopic, recordNow, requests);
        } else {                                                                            // Nếu có rồi, cần cập nhật trạng thái Best và Previous trước khi lưu kết quả mới vào NOW
            UserTopicRecord recordBest = createUserTopicRecordByStage(topicId, userId, LearningStage.BEST);
            UserTopicRecord recordPrev = createUserTopicRecordByStage(topicId, userId, LearningStage.PREVIOUS);
            UserTopicRecord recordNow = checkList.get();

            // Cập nhật trạng thái previous
            recordPrev.setTestTime(recordNow.getTestTime());
            recordPrev.setRightAnswers(recordNow.getRightAnswers());

            // Cập nhật trạng thái best
            if ((recordNow.getRightAnswers() == recordBest.getRightAnswers() && recordNow.getTestTime() < recordBest.getTestTime()) || recordNow.getRightAnswers() > recordBest.getRightAnswers()) {
                recordBest.setRightAnswers(recordNow.getRightAnswers());
                recordBest.setTestTime(recordNow.getTestTime());
            }

            updateTestVocabResult(userTopic, recordNow, requests);
        }
    }

    public void updateTestVocabResult(UserTopic userTopic, UserTopicRecord recordNow, List<TestVocabResultRequest> requests) {

        Long totalNumber = userTopicVocabRepo.countDistinctByUserTopic_Id(userTopic.getId());
        Long passVocabs = requests.stream().filter(v -> v.isStatus()).count();
        int testTime = requests.stream().mapToInt(v -> v.getTestTime()).sum();


        if (passVocabs == totalNumber) {
            if (userTopic.getStatus() != TopicState.PASS) {
                userTopic.setStatus(TopicState.PASS);                //Sau khi làm bài test, nếu trả lời đúng 100% thì PASS và mở bài tiếp theo không thì CONTINUE
                openNextUserTopic(userTopic);
            }

        } else {
            if (userTopic.getStatus() != TopicState.PASS) {
                userTopic.setStatus(TopicState.CONTINUE);
            }
        }
        userTopicRepo.save(userTopic);

        //Update test time và số câu trả lời đúng từ request
        recordNow.setRightAnswers(passVocabs.intValue());
        recordNow.setTestTime(testTime);


        //Update UserTopicVocab
        for (TestVocabResultRequest v : requests) {
            Optional<UserTopicVocab> o_userTopicVocab = userTopicVocabRepo.findByUserTopic_IdAndVocab_Id(userTopic.getId(), v.getVocabId());
            if (o_userTopicVocab.isEmpty()) {
                throw new NotFoundException("Không tìm thấy từ vựng có id = " + v.getVocabId() + " trong userTopic có id = " + userTopic.getId());
            }
            UserTopicVocab userTopicVocab = o_userTopicVocab.get();
            userTopicVocab.setStatus(v.isStatus());
            userTopicVocabRepo.save(userTopicVocab);
        }

    }


    // Set userTopic tiếp theo ở trạng thái NOW nếu pass topic trước
    public void openNextUserTopic(UserTopic curUserTopic) {
        Course course = curUserTopic.getUserCourse().getCourse();
        List<Topic> topicList = topicRepo.findTopicsByCourse_Id(course.getId());
        Topic curTopic = curUserTopic.getTopic();
        Long idNext = 0L;
        for (int i = 0; i < topicList.size(); i++) {
            if (topicList.get(i).getId() == curTopic.getId()) {
                idNext = topicList.get(i + 1).getId();
                break;
            }
        }
        Topic nextTopic = topicRepo.findTopicById(idNext, Topic.class).get();
        UserTopic userTopic = createPendingUserTopic(nextTopic.getId(), curUserTopic.getUserCourse().getUser().getId());
        userTopic.setStatus(TopicState.NOW);
        userTopicRepo.save(userTopic);

    }


    //    Show kết quả test
    public List<VocabTestResultDto> getVocabsTestResult(Long topicId, String userId) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);
        return userTopicVocabRepo.findByUserTopic_Id(userTopic.getId());
    }

    public List<UserTopicRecord> getTopicRecords(Long topicId, String userId) {
        UserTopic userTopic = isUserTopicExist(topicId, userId);
        return userTopicRecordRepo.findByUserTopic_Id(userTopic.getId());
    }

    public TopicState getUserTopicState(Long topicId, String userId){
        UserTopic userTopic=isUserTopicExist(topicId, userId);
        return userTopic.getStatus();

    }

    public List<UserTopicRankDto> getTopTenRank(Long topicId) {
        Topic topic = isTopicExist(topicId);
        List<UserTopicRecord> recordList=userTopicRecordRepo.getTopTenOfUserTopic(topicId);
        List<UserTopicRankDto> rankDtoList=new ArrayList<>();
        for (int i=0;i<recordList.size();i++){
            UserTopicRankDto rankDto=UserTopicRankDto.builder()
                    .rank(i+1)
                    .userName(recordList.get(i).getUserTopic().getUserCourse().getUser().getUserName())
                    .userImg(recordList.get(i).getUserTopic().getUserCourse().getUser().getAvatar())
                    .testTime(recordList.get(i).getTestTime())
                    .rightAnswers(recordList.get(i).getRightAnswers())
                    .build();
            rankDtoList.add(rankDto);
        }
        return rankDtoList;
    }

    public UserTopicRankDto getUserTopicRank(Long topicId, String userId){

        UserTopic userTopic=isUserTopicExist(topicId, userId);
        Optional<UserTopicRecord> recordNow=userTopicRecordRepo.findByStageAndUserTopic_Id(LearningStage.NOW, userTopic.getId());
        if (recordNow.isEmpty()){
            throw new NotFoundException("Không tìm thấy kết quả test hiện tại");
        }
        Optional<Integer> o_userRank= userTopicRecordRepo.getUserTopicRank(topicId, userId);
        if (o_userRank.isEmpty()){
            throw new NotFoundException("Không tìm thấy kết quả rank");
        }

        UserTopicRankDto rank=UserTopicRankDto.builder()
                .rank(o_userRank.get())
                .userName(recordNow.get().getUserTopic().getUserCourse().getUser().getUserName())
                .userImg(recordNow.get().getUserTopic().getUserCourse().getUser().getAvatar())
                .testTime(recordNow.get().getTestTime())
                .rightAnswers(recordNow.get().getRightAnswers())
                .build();
        return rank;

    }

//    Learning Sentence Category

    public List<Sentence> getTopicSentenceToLearn(Long topicId){
        return sentenceRepo.findByTopics_Id(topicId);
    }

    public List<ContextDto> getContextsBySentence(Long sentenceId){
        Sentence sentence=isSentenceExist(sentenceId);
        List<Context> contexts=contextRepo.findBySentence_Id(sentence.getId(), Context.class);
        List<ContextDto> contextDtos=contexts.stream().map(context -> modelMapper.map(context,ContextDto.class)).collect(Collectors.toList());

        return renderContextAvatar(contextDtos);
    }
    public List<ContextDto> renderContextAvatar(List<ContextDto> contextDtos){
        Map<Integer,String> person=new HashMap<>();
        int male=1,female=1;
        for (ContextDto c:contextDtos){
            if(person.containsKey(c.getPersonNumber())){
                c.setImg(person.get(c.getPersonNumber()));
            }else {
                if (c.getGender()== Gender.MALE){
                    c.setImg("asset/img/common/male-talk-"+male);
                    male++;
                }else {
                    c.setImg("asset/img/common/female-talk-"+female);
                    female++;
                }
                person.put(c.getPersonNumber(),c.getImg());
            }
        }
        return contextDtos;
    }





//    Helper Class

    public Topic isTopicExist(Long topicId) {
        Optional<Topic> o_topic = topicRepo.findTopicById(topicId, Topic.class);                     // Kiểm tra topicID tồn tại không
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
        Topic topic = isTopicExist(topicId);
        Users user=isUserExist(userId);
        Optional<UserTopic> o_userTopic = userTopicRepo.findByTopic_IdAndUserCourse_User_Id(topic.getId(), user.getId(), UserTopic.class);
        if (o_userTopic.isEmpty()) {
            throw new BadRequestException("Không tìm thấy UserTopic");
        }
        return o_userTopic.get();
    }

    public Sentence isSentenceExist(Long sentenceId){
        Optional<Sentence> o_sentence=sentenceRepo.findById(sentenceId);
        if(o_sentence.isEmpty()){
            throw new NotFoundException("Không tìm thấy sentence có id = "+sentenceId);
        }
        return o_sentence.get();
    }


}
