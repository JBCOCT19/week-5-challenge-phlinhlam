package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    JobRepository repo;

    @Override
    public void run(String... strings) throws Exception{
        Job job;
      job = new Job("SQL Developer", "Writing SQL codes","11/24/2019","Ivan T.","301-201-2154");
      repo.save(job);


        job = new Job ("Java Developer","Writing codes","11/25/2019","Bin L.","301-521-4587");
        repo.save(job);

        job = new Job("Software Engineer","Implementing applications","11/25/2019","James C.","301-254-8520");
        repo.save(job);

    }
}
