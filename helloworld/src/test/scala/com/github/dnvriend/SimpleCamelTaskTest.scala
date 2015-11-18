/*
 * Copyright 2015 Dennis Vriend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dnvriend

import com.github.dnvriend.activiti.ActivitiImplicits._

class SimpleCamelTaskTest extends TestSpec {
  "Activiti" should "support camel and activemq integration" in {
    val deploymentOperation = repositoryService.createDeployment()
      .addClasspathResource("processes/cameltask.bpmn20.xml")
      .doDeploy

    deploymentOperation should be a 'success

    val variableMap: Map[String, Object] = Map("playroundId" -> "123456")
    val startProcessOperation = runtimeService.startProcessByKey("SimpleCamelCallProcess", variableMap)
    startProcessOperation should be a 'success

    deploymentOperation.foreach { deployment ⇒
      startProcessOperation.foreach { processInstance ⇒
        //        println(processInstance.dump)
      }
      repositoryService.deleteDeploymentById(deployment.id, cascade = true) should be a 'success
    }
  }
}
