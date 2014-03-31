package org.tongqing.jee.rest;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 * User: TQiu
 * Date: 3/31/2014
 */
@Stateless
public class MemberResourceManager {

    @Asynchronous
    public void doSomethingAsynchronously() {
        try {
            System.out.println("start async ... ");
            Thread.sleep(10000);
            System.out.println("end async ... ");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
