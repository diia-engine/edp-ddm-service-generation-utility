/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.generator.constraints.impl;

import com.epam.digital.data.platform.generator.constraints.ConstraintProvider;
import com.epam.digital.data.platform.generator.model.template.Constraint;
import com.epam.digital.data.platform.generator.model.template.Constraint.Content;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import schemacrawler.schema.Column;

@Component
public class MarshalingConstraintProvider implements ConstraintProvider {

  private static final Map<String, Constraint> CONSTRAINTS =
      Map.of(
          LocalDateTime.class.getCanonicalName(),
              from(
                  "com.epam.digital.data.platform.model.core.xmladapter.LocalDateTimeXmlAdapter.class"),
          LocalDate.class.getCanonicalName(),
              from(
                  "com.epam.digital.data.platform.model.core.xmladapter.LocalDateXmlAdapter.class"),
          LocalTime.class.getCanonicalName(),
              from(
                  "com.epam.digital.data.platform.model.core.xmladapter.LocalTimeXmlAdapter.class"));

  @Override
  public List<Constraint> getConstraintForProperty(Column column, String propertyClassName) {
    return Optional.ofNullable(CONSTRAINTS.get(propertyClassName)).stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private static Constraint from(String value) {
    return new Constraint("@javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter",
            List.of(new Content("value", value)));
  }
}