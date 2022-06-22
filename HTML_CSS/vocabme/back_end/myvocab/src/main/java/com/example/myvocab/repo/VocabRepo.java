package com.example.myvocab.repo;

import com.example.myvocab.model.Vocab;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class VocabRepo {
    private ConcurrentHashMap<Integer, Vocab> vocabs;
    public VocabRepo(){
        initData();
    }

    public void initData(){
        vocabs=new ConcurrentHashMap<>();
        vocabs.put(1,new Vocab(1,"accountant","asset/img/word_thumb/accountant.jpg","(n)","asset/mp3/word_pronunciation/accountant.mp3","/əˈkaʊntənt/","a person whose job is to keep or check financial accounts","kế toán viên","Her husband is an _accountant_  of her company.","Người chồng chính là kế toán viên của công ty cô ta.","asset/mp3/word_sentence/accountant.mp3"
        ));
        vocabs.put(2,new Vocab(2,"actor","asset/img/word_thumb/actor.jpg","(n)","asset/mp3/word_pronunciation/actor.mp3","/ˈæktər/","a man who performs on the stage, on television or in films, especially as a profession","nam diễn viên","Who's your favourite _actor_? ","Nam diễn viên yêu thích của bạn là ai?","asset/mp3/word_sentence/actor.mp3"
        ));
        vocabs.put(3,new Vocab(3,"actress","asset/img/word_thumb/actress.jpg","(n)","asset/mp3/word_pronunciation/actress.mp3","/ˈæktrəs/","a woman who performs on the stage, on television or in films, especially as a profession","nữ diễn viên","She's the highest-paid _actress_ in Hollywood.","Cô ấy là nữ diễn viên được trả cát-xê cao nhất ở Hollywood.","asset/mp3/word_sentence/actress.mp3"
        ));
        vocabs.put(4,new Vocab(4,"architect","asset/img/word_thumb/architect.jpg","(n)","asset/mp3/word_pronunciation/architect.mp3","/ˈɑːrkɪtekt/","a person whose job is designing buildings","kiến trúc sư","He is the _architect_ who planned the new shopping centre.","Ông ấy là kiến trúc sư thiết kế ra khu trung tâm thương mại mới.","asset/mp3/word_sentence/architect.mp3"
        ));
        vocabs.put(5,new Vocab(5,"artist","asset/img/word_thumb/artist.jpg","(n)","asset/mp3/word_pronunciation/artist.mp3","/ˈɑːrtɪst/","a person who creates works of art, especially paintings or drawings","họa sĩ","Monet is one of my favourite _artists_.","Monet là một trong những họa sĩ yêu thích của tôi.","asset/mp3/word_sentence/artist.mp3"
        ));
        vocabs.put(6,new Vocab(6,"babysitter","asset/img/word_thumb/babysitter.jpg","(n)","asset/mp3/word_pronunciation/babysitter.mp3","/ˈbeɪbisɪtər/","a person who takes care of babies or children while their parents are away from home and is usually paid to do this","người giữ trẻ","I promised the _babysitter_ that we'd be home by midnight.","Tôi đã hứa với người giữ trẻ rằng chúng tôi sẽ về nhà lúc nửa đêm.","asset/mp3/word_sentence/babysitter.mp3"
        ));
        vocabs.put(7,new Vocab(7,"baker","asset/img/word_thumb/baker.jpg","(n)","asset/mp3/word_pronunciation/baker.mp3","/ˈbeɪkər/","a person whose job is baking and selling bread and cakes","thợ làm bánh","Peter is the best _baker_ in town.","Peter là thợ làm bánh giỏi nhất trong thị trấn.","asset/mp3/word_sentence/baker.mp3"));
        vocabs.put(8,new Vocab(8,"barber","asset/img/word_thumb/barber.jpg","(n)","asset/mp3/word_pronunciation/barber.mp3","/ˈbɑːrbər/","a person whose job is to cut men’s hair and sometimes to shave them","thợ cắt tóc","Our hair salon should hire a skilled _barber_.","Tiệm cắt tóc của chúng tôi cần tuyển một thợ hớt tóc có tay nghề.","asset/mp3/word_sentence/barber.mp3"
        ));
        vocabs.put(9,new Vocab(9,"homemaker","asset/img/word_thumb/homemaker.jpg","(n)","asset/mp3/word_pronunciation/homemaker.mp3","/ˈhəʊmmeɪkər/","a person, especially a woman, who manages a home and takes care of the house and family","người nội trợ","My mother is a good _homemaker_.","Mẹ tôi là một người nội trợ đảm đang.","asset/mp3/word_sentence/homemaker.mp3"
        ));
        vocabs.put(10,new Vocab(10,"carpenter","asset/img/word_thumb/carpenter.jpg","(n)","asset/mp3/word_pronunciation/carpenter.mp3","/ˈkɑːrpəntər/"," a person whose job is making and repairing wooden objects and structures","thợ mộc","Tom's uncle is a skilled _carpenter_.","Chú của Tom là thợ mộc có tay nghề giỏi.","asset/mp3/word_sentence/carpenter.mp3"
        ));
        vocabs.put(11,new Vocab(11,"cashier","asset/img/word_thumb/cashier.jpg","(n)","asset/mp3/word_pronunciation/cashier.mp3","/kæˈʃɪr/","a person whose job is to receive and pay out money in a bank, shop, hotel, etc.","nhân viên thu ngân","Service attitude of the _cashier_ is very friendly.","Thái độ phục vụ của nhân viên thu ngân này rất thân thiện.","asset/mp3/word_sentence/cashier.mp3"
        ));
        vocabs.put(12,new Vocab(12,"chef","asset/img/word_thumb/chef.jpg","(n)","asset/mp3/word_pronunciation/chef.mp3","/ʃef/","a person whose job is to cook, especially the most senior person in a restaurant, hotel, etc.","đầu bếp, bếp trưởng","He is one of the top _chefs_ in Britain.","Ông ấy là một trong những đầu bếp hàng đầu ở Anh.","asset/mp3/word_sentence/chef.mp3"));
    }

    public ConcurrentHashMap<Integer, Vocab> getVocabs(){
        return vocabs;
    }



}
