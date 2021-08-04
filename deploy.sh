#!/bin/bash

export NAMESPACE="${1}"
shift

if [ -z "${NAMESPACE}" ]; then
  echo "Namespace not defined. Exiting"
  exit 1
fi

kubectl apply -f service.yml -n "${NAMESPACE}"

sleep 60

export SELF_IP=$(kubectl get services --namespace "${NAMESPACE}" e2e-ext-app-service --output jsonpath='{.status.loadBalancer.ingress[0].ip}')

echo "IP: "${SELF_IP}""

sed s/{{NAMESPACE}}/$NAMESPACE/g deployment.yml > deployment_generated.yml
sed -i '' s/{{SELF_IP}}/$SELF_IP/g deployment_generated.yml

kubectl apply -f deployment_generated.yml -n "${NAMESPACE}"

sleep 60

POD=$(kubectl get pod --namespace "${NAMESPACE}" --selector="app=e2e-ext-app" --output jsonpath='{.items[0].metadata.name}')
kubectl exec -it "${POD}" --namespace "${NAMESPACE}" -- bash -c 'openssl s_client -connect "${TARGET_NAMESPACE}.gke-03.epod.dev.symphony.com:443" > epod.cert'
kubectl exec -it "${POD}" --namespace "${NAMESPACE}" -- bash -c '$JAVA_HOME/bin/keytool -import -noprompt -trustcacerts -alias epod -file epod.cert -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit'

rm deployment_generated.yml

echo "kubectl exec -it ${POD} --namespace ${NAMESPACE} -- bash"
echo "Go to  https://${SELF_IP}:4000/bundle.json to check."

exit 0
