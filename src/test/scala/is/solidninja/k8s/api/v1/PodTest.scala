package is.solidninja
package k8s
package api
package v1

import org.scalatest.{FreeSpec, Matchers}

import io.circe._
import io.circe.literal._

import JsonProtocol._

class PodTest extends FreeSpec with Matchers {

  "Pod v1" - {
    "should decode from a simple example" in {
      val j: Json = json"""{
        "metadata": {
          "name": "mongodb-1-deploy",
          "namespace": "myproject",
          "labels": {},
          "annotations": {}
        },
        "spec": {
          "volumes": [],
          "containers": [
            {
              "name": "deployment",
              "image": "openshift/origin-deployer:v1.5.1",
              "env": [],
              "resources": {},
              "volumeMounts": [],
              "terminationMessagePath": "/dev/termination-log",
              "imagePullPolicy": "IfNotPresent"
            }
          ],
          "restartPolicy": "Never",
          "terminationGracePeriodSeconds": 10,
          "activeDeadlineSeconds": 21600,
          "dnsPolicy": "ClusterFirst",
          "serviceAccountName": "deployer",
          "serviceAccount": "deployer",
          "nodeName": "172.22.22.60"
        },
        "status": {
          "phase": "Failed",
          "conditions": [
            {
              "type": "Initialized",
              "status": "True",
              "lastProbeTime": null,
              "lastTransitionTime": "2017-06-15T15:16:06Z"
            },
            {
              "type": "Ready",
              "status": "False",
              "lastProbeTime": null,
              "lastTransitionTime": "2017-06-15T15:16:09Z",
              "reason": "ContainersNotReady",
              "message": "containers with unready status: [deployment]"
            },
            {
              "type": "PodScheduled",
              "status": "True",
              "lastProbeTime": null,
              "lastTransitionTime": "2017-06-15T15:16:06Z"
            }
          ],
          "hostIP": "172.22.22.60",
          "startTime": "2017-06-15T15:16:06Z",
          "containerStatuses": [
            {
              "name": "deployment",
              "state": {
                "terminated": {
                  "exitCode": 1,
                  "reason": "Error",
                  "startedAt": "2017-06-15T15:16:07Z",
                  "finishedAt": "2017-06-15T15:16:08Z",
                  "containerID": "docker://faac571a11bc7daada70c0f01b333bee26f88ff89fb75f988f70f84d5ae15f5f"
                }
              },
              "lastState": {},
              "ready": false,
              "restartCount": 0,
              "image": "openshift/origin-deployer:v1.5.1",
              "imageID": "docker-pullable://openshift/origin-deployer@sha256:77ac551235d8edf43ccb2fbd8fa5384ad9d8b94ba726f778fced18710c5f74f0",
              "containerID": "docker://faac571a11bc7daada70c0f01b333bee26f88ff89fb75f988f70f84d5ae15f5f"
            }
          ]
        }
      }"""

      // FIXME - how to get rid of copy-pasted material?

      val meta = ObjectMeta(
        objectName = "mongodb-1-deploy",
        namespace = Namespace("myproject"),
        labels = Map.empty,
        annotations = Annotations(Map.empty)
      )

      val podSpec = PodSpec(
        volumes = Some(Nil),
        containers = List(
          Container(
            image = ImageName("openshift/origin-deployer:v1.5.1"),
            imagePullPolicy = "IfNotPresent",
            args = None,
            command = None,
            env = Some(Nil)
          )
        )
      )

      val expected = Pod(
        metadata = Some(meta),
        spec = podSpec
      )

      j.as[Pod] should equal(Right(expected))
      // FIXME - add test for serialization back to json
    }
  }

}
