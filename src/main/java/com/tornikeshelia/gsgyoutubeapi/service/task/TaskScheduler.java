package com.tornikeshelia.gsgyoutubeapi.service.task;

import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.GsgUser;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.entity.YoutubeLink;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.GsgUserRepository;
import com.tornikeshelia.gsgyoutubeapi.model.persistence.repository.YoutubeLinkRepository;
import com.tornikeshelia.gsgyoutubeapi.service.youtube.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskScheduler {

    @Autowired
    private YoutubeService youtubeService;

    @Autowired
    private GsgUserRepository userRepository;

    @Autowired
    private YoutubeLinkRepository youtubeLinkRepository;


    /**
     *
     *
     * Right now, the YoutubeUpdate by Trigger time is solved in Angular UI of this project. But this method shows how I would solve it in API
     *
     * Gets only authenticated users and updates their YoutubeLink by trigger time
     *
     *
     * **/
    @Scheduled(cron = "0 * * * * *")
    public void updateYoutube() {
        List<GsgUser> authenticatedUsers = userRepository.getAuthenticatedUsers();
        for (GsgUser user : authenticatedUsers) {
            int jobTriggerTime = user.getJobTriggerTime();
            long todayInMillis = new Date().getTime();
            YoutubeLink youtubeLink = youtubeLinkRepository.getByUser(user);
            if (youtubeLink == null) {
                youtubeService.saveByUserAndYoutube(user, null);
            } else {
                long lastUpdateMillis = youtubeLink.getUpdateDate() != null ? youtubeLink.getUpdateDate().getTime() : youtubeLink.getCreationDate().getTime();
                if (todayInMillis - lastUpdateMillis >= jobTriggerTime * 60000) {
                    youtubeService.saveByUserAndYoutube(user, youtubeLink);
                }
            }

        }

    }


}
